package com.nelumbo.parking.back.repositories;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nelumbo.parking.back.DTO.EnteringDTO;
import com.nelumbo.parking.back.entities.Entering;

public interface IEnteringRepository extends JpaRepository<Entering, Long> {

	public Optional<Entering> findOneByVehicle_plate(String plate);

	public List<Entering> findByParking_idparking(Long id);
	
    public Optional<Entering> findOneByVehicle_idvehicle(Long idevehicle); 
    
    @Query(value="select new com.nelumbo.parking.back.DTO.EnteringDTO(e.date, e.parking.name, e.vehicle.plate )from Entering as e where e.identering= :id ")
    public Optional<EnteringDTO> findOneDTOById(Long id);
    
   

}
