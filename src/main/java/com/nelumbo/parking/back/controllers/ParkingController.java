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
import com.nelumbo.parking.back.models.dto.TextResponseDTO;
import com.nelumbo.parking.back.models.dto.TimeHoursDTO;
import com.nelumbo.parking.back.models.dto.VehicleEntDTO;
import com.nelumbo.parking.back.models.dto.VehicleRankDTO;
import com.nelumbo.parking.back.models.entities.Vehicle;
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
	public Map<String, List<VehicleEntDTO>> getVehiclesAct(@PathVariable Long id,HttpServletRequest request) {
		
		dataAccessFilter.parkingAccessIdFilter(request,id);
		Map<String, List<VehicleEntDTO>> response = new HashMap<>();
	
		response.put("vehicles", parkingService.entVehicleDetails(id));
		return response;
	}
	
	//Revisado
	@GetMapping(path="active/vehicle/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public VehicleEntDTO getVehicleAct(@PathVariable Long id) {

		return parkingService.vehicleById(id);
	}
	
	
	
	@GetMapping(path="/user/{iduser}/vehiclesfirst",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<Vehicle>> getVehicleFistTimeByUser(@PathVariable Long iduser,HttpServletRequest request) {
		dataAccessFilter.userAccessIdFilter(request, iduser);
		Map<String, List<Vehicle>> response = new HashMap<>();
		
		response.put("vehicles",parkingService.vehiclesFirstTimeByUser(iduser));
		
		return response;
	}
	
	
	@GetMapping(path="user/{iduser}/vehiclesrep",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<Vehicle>> getVehicleRepeatedlyByUser(@PathVariable Long iduser,HttpServletRequest request) {
		dataAccessFilter.userAccessIdFilter(request, iduser);
		Map<String, List<Vehicle>> response = new HashMap<>();
		
		response.put("vehicles",parkingService.vehiclesRepeatedlyByUser(iduser));
		
		return response;
	}
	
	//Revisado
	@GetMapping(path="/{id}/vehiclesrank",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<VehicleRankDTO>> getVehiclesRank(@PathVariable Long id,HttpServletRequest request) {
		dataAccessFilter.parkingAccessIdFilter(request,id);
		 Map<String, List<VehicleRankDTO>> response = new HashMap<>();
			
			response.put("vehicles",parkingService.rankVehicles(id));
			
			return response;
	}
	
	//Revisado
	@GetMapping(path="/{id}/average/datemin/{dateMin}/datemax/{dateMax}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public TextResponseDTO getAverageUseById(@PathVariable String dateMin,@PathVariable String dateMax,@PathVariable Long id,HttpServletRequest request) throws ParseException {
		
		dataAccessFilter.parkingAccessIdFilter(request,id);
		Date dateMin2 = parkingService.parseDate(dateMin);
		Date dateMax2 = parkingService.parseDate(dateMax);
		return new TextResponseDTO("Parqueadero con promedio de "+parkingService.averageUsageByid(
				dateMin2,
				dateMax2,
				id,
				parkingService.getDays(dateMin2, dateMax2))+" entradas por día");
	
	}
	
	
	
	
	@GetMapping(path="average/user/{iduser}/datemin/{dateMin}/datemax/{dateMax}",
			consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public TextResponseDTO getAverageUseAllByUser(@PathVariable String dateMin,@PathVariable String dateMax,
			@PathVariable Long iduser ,HttpServletRequest request) throws ParseException {
		
		dataAccessFilter.parkingAccessIdUserFilter(request,iduser);
		Date dateMin2 = parkingService.parseDate(dateMin);
		Date dateMax2 = parkingService.parseDate(dateMax);
		
		return new TextResponseDTO("Parqueaderos con promedio de "+parkingService.averageUsageAllByUser(
				dateMin2,
				dateMax2,
				parkingService.getDays(dateMin2, dateMax2), iduser)+" entradas por día");
	}
	

	//Revisado--
	@GetMapping(path="/{id}/averagehours",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public TextResponseDTO getAverageHours(@PathVariable Long id,HttpServletRequest request) {
		
		dataAccessFilter.parkingAccessIdFilter(request,id);		
		TimeHoursDTO time=parkingService.averageHoursById(id);
		return new TextResponseDTO("El parqueadero posee un promedio de "+ time.horas()+" horas "+
		time.minutos()+" minutos y "+time.segundos()+" segundos de uso por vehículo");
	
	}
	
	
	

	
	//Revisado 
	@PutMapping(path="/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public ParkingDTO updateParking(@RequestBody ParkingDTO parking, @PathVariable Long id,HttpServletRequest request) {
 
		dataAccessFilter.parkingAccessIdFilter(request,id);

		return parkingService.updateDTO(parking, id);

	}

	

}
