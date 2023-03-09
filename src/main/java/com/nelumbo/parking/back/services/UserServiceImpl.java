package com.nelumbo.parking.back.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.nelumbo.parking.back.DTO.EmailContentDTO;
import com.nelumbo.parking.back.DTO.ParkingDTO;
import com.nelumbo.parking.back.DTO.ParkingVehicleDTO;
import com.nelumbo.parking.back.entities.Parking;
import com.nelumbo.parking.back.entities.User;
import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.exceptions.RequestException;
import com.nelumbo.parking.back.repositories.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IParkingService parkingService;
	
	@Autowired
	IVehicleService vehicleService;
	
	@Autowired
    RestTemplate restTemplate;

	@Override
	public User findById(Long id) {

		return userRepository.findById(id).orElseThrow(() ->  new RequestException("El usuario con ID: " + id + " no existe en la base de datos") );
	}

	@Override
	public User create(User user) {
		return userRepository.save(user);
	}

	@Override
	public void associateParking(Long idUser, Long idParking) {
		
		Parking parking = parkingService.findById(idParking);
		if (!(parking.getUser() == null)) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El parqueadero ya tiene un socio asignado");
		}
		
		parking.setUser(this.findById(idUser));
		parkingService.updateWhitUser(parking, idParking);
	}

	@Override
	public List<ParkingDTO> findAllParkings(Long id) {

		return parkingService.findAllByUser(id);
	}

	@Override
	public User findByName(String name) {
		return userRepository.findOneByName(name);
	}

	@Override
	public List<ParkingVehicleDTO> findAllParkingsInd(Long id) {
		this.findById(id);
		List<ParkingVehicleDTO> parkings = parkingService.findAllByUserInd(id);
		List<ParkingVehicleDTO> parkingsVeh = new ArrayList<>();
		for (ParkingVehicleDTO p : parkings) {
			if (!p.getVehicles().isEmpty()) {
				parkingsVeh.add(p);
			}
		}
		return parkingsVeh;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> sendEmail(EmailContentDTO emailContent) {
		
		parkingService.findOneByName(emailContent.getParking());
		vehicleService.findOneByPlate(emailContent.getPlate());
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<EmailContentDTO> entity = new HttpEntity<EmailContentDTO>(emailContent,headers);
	      
	      return restTemplate.exchange(
	    		  "http://localhost:8090/emails", HttpMethod.POST, entity, Map.class).getBody();
	}

}
