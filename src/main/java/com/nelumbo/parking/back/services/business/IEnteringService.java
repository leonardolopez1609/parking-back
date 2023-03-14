package com.nelumbo.parking.back.services.business;

import java.util.List;
import com.nelumbo.parking.back.models.dto.EnteringDTO;
import com.nelumbo.parking.back.models.entities.Entering;

public interface IEnteringService {

	EnteringDTO findDTOById(Long id);
	
	Entering findById(Long id);

	List<Entering> findAllByParking_idparking(Long idparking);

	List<Entering> findAllByParking_idparkingInd(Long idparking);

	Entering findOneByPlate(String plate);

	Entering create(Long idparking, String plate);

	void deleteByID(Long id);

	boolean vehicleIsPresent(String plate);
	
	void deleteAllByList(List<Entering> enterings);

	List<Entering> findAll();

}
