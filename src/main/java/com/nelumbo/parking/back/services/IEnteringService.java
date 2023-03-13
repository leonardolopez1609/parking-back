package com.nelumbo.parking.back.services;

import java.util.List;
import com.nelumbo.parking.back.DTO.EnteringDTO;
import com.nelumbo.parking.back.entities.Entering;

public interface IEnteringService {

	EnteringDTO findById(Long id);

	List<Entering> findAllByParking_idparking(Long idparking);

	List<Entering> findAllByParking_idparkingInd(Long idparking);

	Entering findOneByPlate(String plate);

	Entering create(Long idparking, String plate);

	void deleteByID(Long id);

	boolean vehicleIsPresent(String plate);
	
	void deleteAllByList(List<Entering> enterings);

}
