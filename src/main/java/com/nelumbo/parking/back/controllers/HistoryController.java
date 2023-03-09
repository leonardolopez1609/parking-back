package com.nelumbo.parking.back.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nelumbo.parking.back.entities.History;
import com.nelumbo.parking.back.services.IHistoryService;
import com.nelumbo.parking.back.DTO.VehicleHistoryDTO;


@RestController
@RequestMapping("/histories")
@CrossOrigin(origins = { "*" })
public class HistoryController {

	@Autowired
	private IHistoryService historyService;
	
	
    //Revisado
	@GetMapping(path="/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public History getHistory(@PathVariable Long id) {
		return historyService.findById(id).get();
	}
	
	//Revisado
	@GetMapping(path="vehicles/datemin/{dateMin}/datemax/{dateMax}/in/parking/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VehicleHistoryDTO> getAverageUseById(@PathVariable String dateMin,@PathVariable String dateMax,@PathVariable Long id) throws ParseException {

		Date dateMin2 = historyService.parseDate(dateMin);
		Date dateMax2 = historyService.parseDate(dateMax);
		
		 historyService.getDays(dateMin2, dateMax2);
		
		return historyService.getHistoryByRangeDate(
				dateMin2,
				dateMax2,
				id);
	}
	
	//Revisado
	@GetMapping(path="vehicles/datemin/{dateMin}/datemax/{dateMax}/in/parking/{id}/plate/{plate}",
			consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public  Map<String, Object> getAverageUseById(@PathVariable String dateMin,@PathVariable String dateMax,@PathVariable Long id,@PathVariable String plate) throws ParseException {

		Map<String, Object> response = new HashMap<>();
		
		Date dateMin2 = historyService.parseDate(dateMin);
		Date dateMax2 = historyService.parseDate(dateMax);
		
		
		response.put("vehicles", historyService.getHistoryByRangeDateAndPlate(
				dateMin2,
				dateMax2,
				id,plate));
			
		return response;
	}

 
	//Revisado
	@PostMapping(path="/departure/vehicle/{plate}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> createHistory(@PathVariable String plate) {
		Map<String, Object> response = new HashMap<>();
		historyService.create(plate);
		response.put("mensaje", "Salida registrada");

		return response;

	}
}
