package com.nelumbo.parking.back.services.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.exceptions.RequestException;
import com.nelumbo.parking.back.models.dto.EmailContentDTO;
import com.nelumbo.parking.back.models.dto.ParkingDTO;
import com.nelumbo.parking.back.models.dto.ParkingVehicleDTO;
import com.nelumbo.parking.back.models.dto.UserDTO;
import com.nelumbo.parking.back.models.entities.Parking;
import com.nelumbo.parking.back.models.entities.User;
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
	public List<ParkingVehicleDTO> findAllVehiclesInParkingsInd(Long id) {
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
		
		parkingService.findById(emailContent.getIdparking());
		vehicleService.findOneByPlate(emailContent.getPlate());
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<EmailContentDTO> entity = new HttpEntity<EmailContentDTO>(emailContent,headers);
	      
	      return restTemplate.exchange(
	    		  "http://localhost:8090/emails", HttpMethod.POST, entity, Map.class).getBody();
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email) ;
	}

	@Override
	public User save(User user) {
		
		return userRepository.save(user);
	}

	@Override
	public UserDTO findDTOById(Long id) {
		User user= this.findById(id);
		return new UserDTO(user.getName(),user.getEmail(),user.getRole().toString()) ;
	}

	@Override
	public List<ParkingVehicleDTO> findAllVehiclesInAllParkingsInd() {
		
		List<ParkingVehicleDTO> parkings = parkingService.findAllWithVehiclesInd();
		List<ParkingVehicleDTO> parkingsVeh = new ArrayList<>();
		for (ParkingVehicleDTO p : parkings) {
			if (!p.getVehicles().isEmpty()) {
				parkingsVeh.add(p);
			}
		}
		return parkingsVeh;
		
	}

	

}
