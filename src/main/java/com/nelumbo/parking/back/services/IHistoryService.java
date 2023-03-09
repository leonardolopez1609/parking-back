package com.nelumbo.parking.back.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.nelumbo.parking.back.DTO.VehicleHistoryDTO;
import com.nelumbo.parking.back.entities.History;

public interface IHistoryService {

	Optional<History> findById(Long id);

	List<VehicleHistoryDTO> getHistoryByRangeDateAndPlate(Date min, Date max, Long idParking, String plate);

	History create(String plate);

	Date parseDate(String date);

	int getDays(Date dateMin, Date dateMax);
	
	void deleteAllByList(List<History> histories);

}
