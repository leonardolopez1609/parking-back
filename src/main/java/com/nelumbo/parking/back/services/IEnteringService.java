package com.nelumbo.parking.back.services;

import java.util.List;
import java.util.Optional;
import com.nelumbo.parking.back.entities.Entering;

public interface IEnteringService {

	Optional<Entering> findById(Long id);

	List<Entering> findAllByParking_idparking(Long idparking);

	List<Entering> findAllByParking_idparkingInd(Long idparking);

	Optional<Entering> findOneByPlate(String plate);

	Entering create(Long idparking, String plate);

	void deleteByID(Long id);

	boolean vehicleIsPresent(String plate);
	
	void deleteAllByList(List<Entering> enterings);

}
