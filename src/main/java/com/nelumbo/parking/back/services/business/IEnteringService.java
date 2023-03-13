package com.nelumbo.parking.back.services.business;

import java.util.List;
import java.util.Optional;

import com.nelumbo.parking.back.models.dto.EnteringDTO;
import com.nelumbo.parking.back.models.entities.Entering;
import com.nelumbo.parking.back.models.entities.Parking;

public interface IEnteringService {

	EnteringDTO findById(Long id);

	List<Entering> findAllByParking_idparking(Long idparking);

	List<Entering> findAllByParking_idparkingInd(Long idparking);

	Entering findOneByPlate(String plate);

	Entering create(Long idparking, String plate);

	void deleteByID(Long id);

	boolean vehicleIsPresent(String plate);
	
	void deleteAllByList(List<Entering> enterings);

	List<Entering> findAll();

}
