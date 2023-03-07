package com.nelumbo.parking.back.services;

import java.util.List;
import java.util.Optional;

import com.nelumbo.parking.back.DTO.EmailContentDTO;
import com.nelumbo.parking.back.DTO.ParkingDTO;
import com.nelumbo.parking.back.DTO.ParkingVehicleDTO;
import com.nelumbo.parking.back.entities.User;

import jakarta.validation.Valid;

public interface IUserService {
	
	Optional<User> findById(Long id);
	
	List<ParkingVehicleDTO> findAllParkingsInd(Long id);
	
	List<ParkingDTO> findAllParkings(Long id);

	User findByName(String name);

	User create(User user);
	
	void associateParking(Long idUser, Long idParking);

	String sendEmail(EmailContentDTO user);

	}
