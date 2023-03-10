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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nelumbo.parking.back.DTO.ParkingDTO;
import com.nelumbo.parking.back.DTO.VehicleEntDTO;
import com.nelumbo.parking.back.entities.Parking;
import com.nelumbo.parking.back.services.IParkingService;
import com.nelumbo.parking.back.DTO.TimeHoursDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/parkings")
@CrossOrigin(origins = { "*" })
public class ParkingController {

	
	
	//AGREGAR EL GET DE TODOS LOS PARQUEADEROS.
	
	@Autowired
	private IParkingService parkingService;

	
	//Revisado
	@GetMapping(path="/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ParkingDTO getParking(@PathVariable Long id) {
           return parkingService.findDTOByID(id);	
	}
	
	//Revisado
		@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
		public Map<String, Object> getParkings() {
			Map<String, Object> response = new HashMap<>();
			response.put("parkings", parkingService.findAll());
			return response;
		}

	//Revisado
	@GetMapping(path="/user/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ParkingDTO> getParkings(@PathVariable Long id) {
        
		return parkingService.findAllByUser(id);
	}
    
	//Revisado
	@GetMapping(path="/{id}/vehiclesact",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getVehiclesAct(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
	
		response.put("vehicles", parkingService.entVehicleDetails(id));
		return response;
	}
	
	//Revisado
	@GetMapping(path="active/vehicle/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public VehicleEntDTO getVehicleAct(@PathVariable Long id) {

		return parkingService.vehicleById(id);
	}
	
	//Revisado
	@GetMapping(path="vehiclesfirst",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getVehicleFistTime() {
		Map<String, Object> response = new HashMap<>();
		
		response.put("vehicles",parkingService.vehiclesFirstTime());
		
		return response;
	}
	
	//Revisado
	@GetMapping(path="vehiclesrep",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getVehicleRepeatedly() {
       Map<String, Object> response = new HashMap<>();
		
		response.put("vehicles",parkingService.vehiclesRepeatedly());
		
		return response;
	}
	
	//Revisado
	@GetMapping(path="/{id}/vehiclesrank",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getVehiclesRank(@PathVariable Long id) {
		 Map<String, Object> response = new HashMap<>();
			
			response.put("vehicles",parkingService.rankVehicles(id));
			
			return response;
	}
	
	//Revisado
	@GetMapping(path="/{id}/average/datemin/{dateMin}/datemax/{dateMax}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getAverageUseById(@PathVariable String dateMin,@PathVariable String dateMax,@PathVariable Long id) throws ParseException {
		
		 Map<String, Object> response = new HashMap<>();
		Date dateMin2 = parkingService.parseDate(dateMin);
		Date dateMax2 = parkingService.parseDate(dateMax);
		response.put("mensaje","Parqueadero con promedio de "+parkingService.averageUsageByid(
				dateMin2,
				dateMax2,
				id,
				parkingService.getDays(dateMin2, dateMax2))+" entradas por día");
		return response;
	}
	
	//Revisado
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
	@GetMapping(path="/{id}/getaveragehours",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getAverageHours(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		TimeHoursDTO time=parkingService.averageHoursById(id);
		
		response.put("mensaje", "El parqueadero posee un promedio de "+ time.horas()+" horas "+
		time.minutos()+" minutos y "+time.segundos()+" segundos de uso por vehículo");
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
	@PutMapping(path="/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String, Object> updateParking(@Valid @RequestBody ParkingDTO parking, @PathVariable Long id) {

		
		Map<String, Object> response = new HashMap<>();

		response.put("parking", parkingService.updateDTO(parking, id));
		response.put("mensaje", "Parquedero actualizado con éxito!");

		return response;

	}

	//Revisado
	@DeleteMapping(path="/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> deleteParking(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		parkingService.delete(id);
		response.put("mensaje", "Parqueadero eliminado con éxito!");

		return response;
	}

}
