package com.nelumbo.parking.back.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nelumbo.parking.back.models.dto.ParkingDTO;
import com.nelumbo.parking.back.models.dto.TextResponseDTO;
import com.nelumbo.parking.back.models.entities.Parking;
import com.nelumbo.parking.back.models.entities.Vehicle;
import com.nelumbo.parking.back.services.business.IParkingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/parkings")
@CrossOrigin(origins = { "*" })
public class ParkingsController {

	@Autowired
	private IParkingService parkingService;

	// Revisado

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<ParkingDTO>> getParkings(HttpServletRequest request) {
		Map<String, List<ParkingDTO>> response = new HashMap<>();
		response.put("parkings", parkingService.findAll());
		return response;
	}


//Revisado
	@GetMapping(path="vehiclesfirst",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<Vehicle>> getVehicleFistTime() {
		Map<String, List<Vehicle>> response = new HashMap<>();
		
		response.put("vehicles",parkingService.vehiclesFirstTime());
		
		return response;
	}
	
	@GetMapping(path="vehiclesrep",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<Vehicle>> getVehicleRepeatedly() {
       Map<String, List<Vehicle>> response = new HashMap<>();
		
		response.put("vehicles",parkingService.vehiclesRepeatedly());
		
		return response;
	}
	
	//Revisado
		@GetMapping(path="average/datemin/{dateMin}/datemax/{dateMax}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
		public TextResponseDTO getAverageUseAll(@PathVariable String dateMin,@PathVariable String dateMax) throws ParseException {
			
			Date dateMin2 = parkingService.parseDate(dateMin);
			Date dateMax2 = parkingService.parseDate(dateMax);
			
			return new TextResponseDTO("Parqueaderos con promedio de "+parkingService.averageUsageAll(
					dateMin2,
					dateMax2,
					parkingService.getDays(dateMin2, dateMax2))+" entradas por día");
		}
		
		//Revisado
		@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseStatus(value = HttpStatus.CREATED)
		public Parking createParking(@Valid @RequestBody ParkingDTO parking) {
			
			return parkingService.create(parking);
		}
		
		//Revisado
		@DeleteMapping(path="/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
		public TextResponseDTO deleteParking(@PathVariable Long id,HttpServletRequest request) {
			
			parkingService.delete(id);
			return new TextResponseDTO("Parqueadero eliminado con éxito!");
		}
}
