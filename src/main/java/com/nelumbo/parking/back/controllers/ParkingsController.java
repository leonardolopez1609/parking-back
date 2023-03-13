package com.nelumbo.parking.back.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
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
	public Map<String, Object> getParkings(HttpServletRequest request) {
		Map<String, Object> response = new HashMap<>();
		response.put("parkings", parkingService.findAll());
		// response.put("token", request.getHeader("Authorization"));
		return response;
	}


//Revisado---Agregar por parqueaderos del socio----solo admin
	@GetMapping(path="vehiclesfirst",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getVehicleFistTime() {
		Map<String, Object> response = new HashMap<>();
		
		response.put("vehicles",parkingService.vehiclesFirstTime());
		
		return response;
	}
	
	@GetMapping(path="vehiclesrep",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getVehicleRepeatedly() {
       Map<String, Object> response = new HashMap<>();
		
		response.put("vehicles",parkingService.vehiclesRepeatedly());
		
		return response;
	}
	
	//Revisado--------Agregar parqueaderos por usuario
		@GetMapping(path="average/datemin/{dateMin}/datemax/{dateMax}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
		public Map<String, Object> getAverageUseAll(@PathVariable String dateMin,@PathVariable String dateMax) throws ParseException {
			
			Map<String, Object> response = new HashMap<>();
			
			
			Date dateMin2 = parkingService.parseDate(dateMin);
			Date dateMax2 = parkingService.parseDate(dateMax);
			
			response.put("mensaje","Parqueaderos con promedio de "+parkingService.averageUsageAll(
					dateMin2,
					dateMax2,
					parkingService.getDays(dateMin2, dateMax2))+" entradas por día");
			
			return response;
		}
		
		//Revisado
		@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseStatus(value = HttpStatus.CREATED)
		public Map<String, Object> createParking(@Valid @RequestBody ParkingDTO parking) {
			Map<String, Object> response = new HashMap<>();

			response.put("parking", parkingService.create(parking));
			response.put("mensaje", "Parqueadero creado con éxito!");

			return response;
		}
		
		//Revisado
		@DeleteMapping(path="/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
		public Map<String, Object> deleteParking(@PathVariable Long id,HttpServletRequest request) {
			Map<String, Object> response = new HashMap<>();
			parkingService.delete(id);
			response.put("mensaje", "Parqueadero eliminado con éxito!");

			return response;
		}
}
