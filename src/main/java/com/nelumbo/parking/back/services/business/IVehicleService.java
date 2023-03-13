package com.nelumbo.parking.back.services.business;

import java.util.List;
import java.util.Optional;

import com.nelumbo.parking.back.models.entities.Vehicle;

public interface IVehicleService {

	Optional<Vehicle> findById(Long id);
	
    Vehicle findOneByPlate(String plate);
	
	List<Vehicle> findFirstTime();
	
    List<Vehicle> findRepeatedly();

	Vehicle create(Vehicle vehicle);
	
	boolean exists(String plate);
    
    void invalidPlate(String plate);

}
