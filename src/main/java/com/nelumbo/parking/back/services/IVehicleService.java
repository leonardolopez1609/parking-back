package com.nelumbo.parking.back.services;

import java.util.List;
import java.util.Optional;
import com.nelumbo.parking.back.entities.Vehicle;

public interface IVehicleService {

	Optional<Vehicle> findById(Long id);
	
    Optional<Vehicle> findOneByPlate(String plate);
	
	List<Vehicle> findFirstTime();
	
    List<Vehicle> findRepeatedly();

	Vehicle create(Vehicle vehicle);
	
	boolean exists(String plate);
    
    void invalidPlate(String plate);

}
