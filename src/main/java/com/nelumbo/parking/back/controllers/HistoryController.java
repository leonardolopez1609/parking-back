package com.nelumbo.parking.back.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/histories")
@CrossOrigin(origins = { "*" })
public class HistoryController {

	@Autowired
	private IHistoryService historyService;
	
	
    //Revisado
	@GetMapping("getone/{id}")
	public ResponseEntity<?> getHistory(@PathVariable Long id) {
		return new ResponseEntity<History>(historyService.findById(id).get(), HttpStatus.OK);
	}
	
	//Revisado
	@GetMapping("getvehicles/min/{dateMin}/max/{dateMax}/in/{id}")
	public ResponseEntity<?> getAverageUseById(@PathVariable String dateMin,@PathVariable String dateMax,@PathVariable Long id) throws ParseException {

		Date dateMin2 = historyService.parseDate(dateMin);
		Date dateMax2 = historyService.parseDate(dateMax);
		
		 historyService.getDays(dateMin2, dateMax2);
		
		return new ResponseEntity<List<VehicleHistoryDTO>>(historyService.getHistoryByRangeDate(
				dateMin2,
				dateMax2,
				id), HttpStatus.OK);
	}
	
	//Revisado
	@GetMapping("getvehicles/min/{dateMin}/max/{dateMax}/in/{id}/plate/{plate}")
	public ResponseEntity<?> getAverageUseById(@PathVariable String dateMin,@PathVariable String dateMax,@PathVariable Long id,@PathVariable String plate) throws ParseException {

		Date dateMin2 = historyService.parseDate(dateMin);
		Date dateMax2 = historyService.parseDate(dateMax);
		
		 historyService.getDays(dateMin2, dateMax2);
		
		return new ResponseEntity<List<VehicleHistoryDTO>>(historyService.getHistoryByRangeDateAndPlate(
				dateMin2,
				dateMax2,
				id,plate), HttpStatus.OK);
	}


	@PostMapping("/departure/{plate}")
	public ResponseEntity<?> createHistory(@PathVariable String plate) {
		Map<String, Object> response = new HashMap<>();
		historyService.create(plate);
		response.put("mensaje", "Salida registrada");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}
}
