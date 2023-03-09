package com.nelumbo.parking.back.repositories;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.nelumbo.parking.back.DTO.VehicleHistoryDTO;
import com.nelumbo.parking.back.entities.History;

public interface IHistoryRepository extends JpaRepository<History, Long> {


      @Query(value="select new  com.nelumbo.parking.back.DTO.VehicleHistoryDTO (h.parking.idparking, h.vehicle.idvehicle, h.vehicle.plate,h.enteringDate, h.departureDate as dep) " 
			 +"from History as h "
			 +"where " 
			 +"h.enteringDate BETWEEN :min and :max "
			 +"AND h.vehicle.plate like %:plate% AND h.parking.idparking=:idparking "
			 +"order by dep desc "
			 )
	    public List<VehicleHistoryDTO> findHistoryByRangeDateAndPlate(Date min, Date max,Long idparking, String plate);
	 
	  

}
