package com.nelumbo.parking.back.repositories;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.nelumbo.parking.back.entities.Vehicle;

public interface IVehicleRepository extends JpaRepository<Vehicle, Long> {

	public Optional<Vehicle> findOneByPlate(String plate);
    
	@Query(value="SELECT v.idvehicle, v.plate FROM vehicle as v INNER JOIN entering AS e ON e.idvehicle= v.idvehicle WHERE NOT EXISTS (SELECT NULL FROM history as h WHERE h.idvehicle = v.idvehicle)",nativeQuery = true)
	public List<Vehicle> findFirstTime();
	
	@Query(value="SELECT v.idvehicle, v.plate FROM vehicle as v INNER JOIN entering AS e ON e.idvehicle= v.idvehicle WHERE EXISTS (SELECT NULL FROM history as h WHERE h.idvehicle = v.idvehicle)",nativeQuery = true)
	public List<Vehicle> findRepeatedly();
	
	
}
