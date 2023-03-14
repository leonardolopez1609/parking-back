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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nelumbo.parking.back.models.dto.ParkingDTO;
import com.nelumbo.parking.back.models.dto.TimeHoursDTO;
import com.nelumbo.parking.back.models.dto.VehicleEntDTO;
import com.nelumbo.parking.back.services.business.IParkingService;
import com.nelumbo.parking.back.services.security.DataAccessFilter;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/parking")
@CrossOrigin(origins = { "*" })
public class ParkingController {

	
	@Autowired
	private IParkingService parkingService;
	
	@Autowired
	private DataAccessFilter dataAccessFilter;

	
	//Revisado
	@GetMapping(path="/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ParkingDTO getParking(@PathVariable Long id,HttpServletRequest request) {
          // System.out.println("------------- Token: "+  request.getHeader("Authorization"));
		
		dataAccessFilter.parkingAccessIdFilter(request,id);
		return parkingService.findDTOByID(id);	
	}
	
	

	//Revisado------access
	@GetMapping(path="/user/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ParkingDTO> getParkings(@PathVariable Long id) {
        
		return parkingService.findAllByUser(id);
	}
    
	//Revisado
	@GetMapping(path="/{id}/vehiclesact",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getVehiclesAct(@PathVariable Long id,HttpServletRequest request) {
		
		dataAccessFilter.parkingAccessIdFilter(request,id);
		Map<String, Object> response = new HashMap<>();
	
		response.put("vehicles", parkingService.entVehicleDetails(id));
		return response;
	}
	
	//Revisado
	@GetMapping(path="active/vehicle/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public VehicleEntDTO getVehicleAct(@PathVariable Long id) {

		return parkingService.vehicleById(id);
	}
	
	
	
	@GetMapping(path="/user/{iduser}/vehiclesfirst",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getVehicleFistTimeByUser(@PathVariable Long iduser,HttpServletRequest request) {
		dataAccessFilter.userAccessIdFilter(request, iduser);
		Map<String, Object> response = new HashMap<>();
		
		response.put("vehicles",parkingService.vehiclesFirstTimeByUser(iduser));
		
		return response;
	}
	
	
	
	
	
	//Revisado---Agregar por parqueaderos del socio-----solo admin
	
	
	
	@GetMapping(path="user/{iduser}/vehiclesrep",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getVehicleRepeatedlyByUser(@PathVariable Long iduser,HttpServletRequest request) {
		dataAccessFilter.userAccessIdFilter(request, iduser);
		Map<String, Object> response = new HashMap<>();
		
		response.put("vehicles",parkingService.vehiclesRepeatedlyByUser(iduser));
		
		return response;
	}
	
	//Revisado
	@GetMapping(path="/{id}/vehiclesrank",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getVehiclesRank(@PathVariable Long id,HttpServletRequest request) {
		dataAccessFilter.parkingAccessIdFilter(request,id);
		 Map<String, Object> response = new HashMap<>();
			
			response.put("vehicles",parkingService.rankVehicles(id));
			
			return response;
	}
	
	//Revisado
	@GetMapping(path="/{id}/average/datemin/{dateMin}/datemax/{dateMax}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getAverageUseById(@PathVariable String dateMin,@PathVariable String dateMax,@PathVariable Long id,HttpServletRequest request) throws ParseException {
		dataAccessFilter.parkingAccessIdFilter(request,id);
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
	
	
	
	
	@GetMapping(path="average/user/{iduser}/datemin/{dateMin}/datemax/{dateMax}",
			consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getAverageUseAllByUser(@PathVariable String dateMin,@PathVariable String dateMax,
			@PathVariable Long iduser ,HttpServletRequest request) throws ParseException {
		
		dataAccessFilter.parkingAccessIdUserFilter(request,iduser);
		Map<String, Object> response = new HashMap<>();
		
		
		Date dateMin2 = parkingService.parseDate(dateMin);
		Date dateMax2 = parkingService.parseDate(dateMax);
		
		response.put("mensaje","Parqueaderos con promedio de "+parkingService.averageUsageAllByUser(
				dateMin2,
				dateMax2,
				parkingService.getDays(dateMin2, dateMax2), iduser)+" entradas por día");
		
		return response;
	}
	

	//Revisado--
	@GetMapping(path="/{id}/averagehours",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getAverageHours(@PathVariable Long id,HttpServletRequest request) {
		dataAccessFilter.parkingAccessIdFilter(request,id);
		Map<String, Object> response = new HashMap<>();
		TimeHoursDTO time=parkingService.averageHoursById(id);
		
		response.put("mensaje", "El parqueadero posee un promedio de "+ time.horas()+" horas "+
		time.minutos()+" minutos y "+time.segundos()+" segundos de uso por vehículo");
		return response;
	}
	
	
	

	
	//Revisado 
	@PutMapping(path="/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String, Object> updateParking(@RequestBody ParkingDTO parking, @PathVariable Long id,HttpServletRequest request) {
 
		dataAccessFilter.parkingAccessIdFilter(request,id);
		Map<String, Object> response = new HashMap<>();

		response.put("parking", parkingService.updateDTO(parking, id));
		response.put("mensaje", "Parquedero actualizado con éxito!");

		return response;

	}

	

}
