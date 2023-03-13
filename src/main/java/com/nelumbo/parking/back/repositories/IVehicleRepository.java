package com.nelumbo.parking.back.repositories;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nelumbo.parking.back.models.entities.Vehicle;

public interface IVehicleRepository extends JpaRepository<Vehicle, Long> {

	public Optional<Vehicle> findOneByPlate(String plate);
    
	@Query(value="SELECT v.idvehicle, v.plate FROM vehicle as v INNER JOIN entering AS e ON e.idvehicle= v.idvehicle WHERE NOT EXISTS (SELECT NULL FROM history as h WHERE h.idvehicle = v.idvehicle)",nativeQuery = true)
	public List<Vehicle> findFirstTime();
	
	@Query(value="SELECT v.idvehicle, v.plate FROM vehicle as v INNER JOIN entering AS e ON e.idvehicle= v.idvehicle WHERE EXISTS (SELECT NULL FROM history as h WHERE h.idvehicle = v.idvehicle)",nativeQuery = true)
	public List<Vehicle> findRepeatedly();

	@Query(value="SELECT v FROM Vehicle as v INNER JOIN Entering AS e ON e.vehicle.idvehicle= v.idvehicle WHERE NOT EXISTS (SELECT h FROM History as h WHERE h.vehicle.idvehicle = v.idvehicle) and e.parking.user.iduser= :iduser")
	public List<Vehicle> findFirstTimeByUser(Long iduser);
	
	@Query(value="SELECT v FROM Vehicle as v INNER JOIN Entering AS e ON e.vehicle.idvehicle= v.idvehicle WHERE EXISTS (SELECT h FROM History as h WHERE h.vehicle.idvehicle = v.idvehicle) and e.parking.user.iduser= :iduser")
	public List<Vehicle> findRepeatedlyByUser(Long iduser);
	
}
