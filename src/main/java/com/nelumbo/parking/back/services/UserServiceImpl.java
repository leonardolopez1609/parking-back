package com.nelumbo.parking.back.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

	@Override
	public Optional<User> findById(Long id) {

		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new RequestException("El usuario con ID: " + id + " no existe en la base de datos!");
		}
		return user;
	}

	@Override
	public User create(User user) {
		if (user==null||user.getName()==null||user.getName().equals("")) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El nombre del usuario es requerido!");
		}
		if(user.getRole()==null) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El Rol del usuario es requerido!");
		}
		if(userRepository.findOneByName(user.getName())!=(null)) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El nombre de usuario ya está registrado!");
		}

		return userRepository.save(user);
	}

	@Override
	public void associateParking(Long idUser, Long idParking) {
		
		Parking parking = parkingService.findById(idParking);
		if (!(parking.getUser() == null)) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El parqueadero ya tiene un socio asignado!");
		}
		
		parking.setUser(this.findById(idUser).get());
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

		if (parkingsVeh.isEmpty()) {
			throw new RequestException("No hay vehiculos en los parqueaderos");
		}
		return parkingsVeh;

	}

}
