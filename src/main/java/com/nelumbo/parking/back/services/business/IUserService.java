package com.nelumbo.parking.back.services.business;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.nelumbo.parking.back.models.dto.EmailContentDTO;
import com.nelumbo.parking.back.models.dto.ParkingDTO;
import com.nelumbo.parking.back.models.dto.ParkingVehicleDTO;
import com.nelumbo.parking.back.models.dto.TextResponseDTO;
import com.nelumbo.parking.back.models.dto.UserDTO;
import com.nelumbo.parking.back.models.entities.User;


public interface IUserService {
	
	User save(User user);
	
	User findById(Long id);
	
	UserDTO findDTOById(Long id);
	
	List<ParkingVehicleDTO> findAllVehiclesInParkingsInd(Long id);
	
	List<ParkingVehicleDTO> findAllVehiclesInAllParkingsInd();
	
	List<ParkingDTO> findAllParkings(Long id);

	User findByName(String name);

	User create(User user);
	
	void associateParking(Long idUser, Long idParking);

	TextResponseDTO sendEmail(EmailContentDTO user);
	
	Optional<User> findByEmail(String email);

	void associateUser(Long idpartner, Long iduser);

	List<UserDTO> findAllUsersByPartnerInd(Long idpartner);
	

	}
