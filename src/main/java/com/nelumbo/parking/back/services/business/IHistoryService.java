package com.nelumbo.parking.back.services.business;

import java.util.Date;
import java.util.List;

import com.nelumbo.parking.back.models.dto.HistoryDTO;
import com.nelumbo.parking.back.models.dto.VehicleHistoryDTO;
import com.nelumbo.parking.back.models.entities.History;

public interface IHistoryService {

	HistoryDTO findById(Long id);

	List<VehicleHistoryDTO> getHistoryByRangeDateAndPlate(Date min, Date max, Long idParking, String plate);

	History create(String plate);

	Date parseDate(String date);

	int getDays(Date dateMin, Date dateMax);
	
	void deleteAllByList(List<History> histories);

}
