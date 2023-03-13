package com.nelumbo.parking.back.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nelumbo.parking.back.models.dto.HistoryDTO;
import com.nelumbo.parking.back.models.dto.VehicleHistoryDTO;
import com.nelumbo.parking.back.models.entities.History;
import com.nelumbo.parking.back.models.entities.Vehicle;

public interface IHistoryRepository extends JpaRepository<History, Long> {


      @Query(value="select new  com.nelumbo.parking.back.models.dto.VehicleHistoryDTO (h.parking.idparking, h.vehicle.idvehicle, h.vehicle.plate,h.enteringDate, h.departureDate as dep) " 
			 +"from History as h "
			 +"where " 
			 +"h.enteringDate BETWEEN :min and :max "
			 +"AND h.vehicle.plate like %:plate% AND h.parking.idparking=:idparking "
			 +"order by dep desc "
			 )
	    public List<VehicleHistoryDTO> findHistoryByRangeDateAndPlate(Date min, Date max,Long idparking, String plate);
	 
      @Query(value="select new  com.nelumbo.parking.back.models.dto.HistoryDTO (h.enteringDate, h.departureDate, h.parking.name, h.vehicle.plate) from History as h where h.idhistory= :id")
      public Optional<HistoryDTO> findOneDTOById(Long id);
      
      
      @Query(value="select h.vehicle as v from History as h where h.parking.idparking= :id")
      public List<Vehicle> findHistoryVehicles(Long id);

}
