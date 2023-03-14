package com.nelumbo.parking.back.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nelumbo.parking.back.models.dto.HistoryDTO;
import com.nelumbo.parking.back.services.business.IHistoryService;
import com.nelumbo.parking.back.services.security.DataAccessFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/histories")
@CrossOrigin(origins = { "*" })
public class HistoryController {

	@Autowired
	private IHistoryService historyService;
	
	@Autowired
	private DataAccessFilter dataAccessFilter;
	
	
    //Revisado
	@GetMapping(path="/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public HistoryDTO getHistory(@PathVariable Long id,HttpServletRequest request) {
		dataAccessFilter.historyAccessIdFilter(request, id);
		return historyService.findDTOById(id);
	}
	
	
	
	//Revisado
	@GetMapping(path="vehicles/datemin/{dateMin}/datemax/{dateMax}/in/parking/{id}/plate/{plate}",
			consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public  Map<String, Object> getAverageUseById(@PathVariable String dateMin,@PathVariable String dateMax,
			@PathVariable Long id,@PathVariable String plate,HttpServletRequest request) throws ParseException {

		dataAccessFilter.parkingAccessIdFilter(request, id);
		Map<String, Object> response = new HashMap<>();
		
		Date dateMin2 = historyService.parseDate(dateMin);
		Date dateMax2 = historyService.parseDate(dateMax);
		
		
		response.put("vehicles", historyService.getHistoryByRangeDateAndPlate(
				dateMin2,
				dateMax2,
				id,plate));
			
		return response;
	}
	
	@GetMapping(path="/vehicles/parking/{idparking}",
			consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public  Map<String, Object> getHistoryVehicles(@PathVariable Long idparking,HttpServletRequest request) throws ParseException {

		
		dataAccessFilter.parkingAccessIdFilter(request, idparking);
		Map<String, Object> response = new HashMap<>();
		
		response.put("vehicles", historyService.getHistoryVehicles(idparking));
			
		return response;
	}

 
	//Revisado
	@PostMapping(path="/departure/vehicle/{plate}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> createHistory(@Valid @PathVariable String plate,HttpServletRequest request) {
		Map<String, Object> response = new HashMap<>();
		
		//filtro que el vehiculo este en su parqueadero
		dataAccessFilter.parkingAccessIdAndPlateFilter(request,plate);
		historyService.create(plate);
		response.put("mensaje", "Salida registrada");

		return response;

	}
}
