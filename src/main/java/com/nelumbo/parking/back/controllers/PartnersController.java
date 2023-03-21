package com.nelumbo.parking.back.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nelumbo.parking.back.models.dto.ParkingDTO;
import com.nelumbo.parking.back.models.dto.ParkingVehicleDTO;
import com.nelumbo.parking.back.models.dto.TextResponseDTO;
import com.nelumbo.parking.back.models.dto.TimeHoursDTO;
import com.nelumbo.parking.back.models.dto.UserDTO;
import com.nelumbo.parking.back.models.dto.VehicleEntDTO;
import com.nelumbo.parking.back.models.dto.VehicleRankDTO;
import com.nelumbo.parking.back.models.entities.Role;
import com.nelumbo.parking.back.models.entities.Vehicle;
import com.nelumbo.parking.back.models.security.AuthenticationResponse;
import com.nelumbo.parking.back.models.security.RegisterRequest;
import com.nelumbo.parking.back.services.business.IHistoryService;
import com.nelumbo.parking.back.services.business.IParkingService;
import com.nelumbo.parking.back.services.business.IUserService;
import com.nelumbo.parking.back.services.security.AuthenticationService;
import com.nelumbo.parking.back.services.security.DataAccessFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/partners")
@CrossOrigin(origins = { "*" })
public class PartnersController {

	//--------------SERVICES----------------
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IParkingService parkingService;
	
	@Autowired
	private IHistoryService historyService;
	
	@Autowired
	private  AuthenticationService service;
	
	@Autowired
	private DataAccessFilter dataAccessFilter;
	
	//------------END SERVICES--------------
	
	//-------------------------------------------PARTNERS URI'S---------------------------------------------------------
	
	@PutMapping(path="/me/associate/users/{iduser}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public TextResponseDTO asignatePartner(@PathVariable Long iduser,HttpServletRequest request) { 
       userService.associateUser(dataAccessFilter.getUserIdToken(request), iduser);
	   return new TextResponseDTO("Usuario asociado con éxito");
	}
	//-----------------------------------------END PARTNERS URI'S-------------------------------------------------------
	
	//-------------------------------------------PARKINGS URI'S---------------------------------------------------------
	
	@GetMapping(path="/parkings/{idparking}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ParkingDTO getParking(@PathVariable Long idparking,HttpServletRequest request) {		
		dataAccessFilter.accessParkingPartnerFilter(request, idparking);
		return parkingService.findDTOByID(idparking);	
	}
	
	
	@GetMapping(path="/me/parkings/{idparking}/average/datemin/{dateMin}/datemax/{dateMax}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public TextResponseDTO getAverageUseById(@PathVariable String dateMin,@PathVariable String dateMax,@PathVariable Long idparking,HttpServletRequest request) throws ParseException {
		dataAccessFilter.accessParkingPartnerFilter(request, idparking);
		Date dateMin2 = parkingService.parseDate(dateMin);
		Date dateMax2 = parkingService.parseDate(dateMax);
		return parkingService.averageUsageByidResponse(dateMin2, dateMax2, idparking, parkingService.getDays(dateMin2, dateMax2));
		
		
	}
	
	@GetMapping(path="/me/parkings/{idparking}/histories/vehicles",
			consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public  Map<String,List<Vehicle>> getHistoryVehicles(@PathVariable Long idparking,HttpServletRequest request) throws ParseException {
		dataAccessFilter.accessParkingPartnerFilter(request, idparking);
		Map<String, List<Vehicle>> response = new HashMap<>();
		response.put("vehicles", historyService.getHistoryVehicles(idparking));			
		return response;
	}
	
	@GetMapping(path="/me/parkings/average/datemin/{dateMin}/datemax/{dateMax}",
			consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public TextResponseDTO getAverageUseAllByUser(@PathVariable String dateMin,@PathVariable String dateMax,
			HttpServletRequest request) throws ParseException {
		Date dateMin2 = parkingService.parseDate(dateMin);
		Date dateMax2 = parkingService.parseDate(dateMax);
		
		return parkingService.averageUsageAllByUserResponse(dateMin2,
				dateMax2,
				parkingService.getDays(dateMin2, dateMax2), dataAccessFilter.getUserIdToken(request));
		
	}
	
	@GetMapping(path="/parkings/{idparking}/averagehours",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public TextResponseDTO getAverageHours(@PathVariable Long idparking,HttpServletRequest request) {
		dataAccessFilter.accessParkingPartnerFilter(request, idparking);
		
		return parkingService.averageHoursResponse(idparking);
	
	}
	
	@GetMapping(path="/parkings/{idparking}/vehiclesact",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<VehicleEntDTO>> getVehiclesAct(@PathVariable Long idparking,HttpServletRequest request) {
		dataAccessFilter.accessParkingPartnerFilter(request, idparking);
		Map<String, List<VehicleEntDTO>> response = new HashMap<>();
		response.put("vehicles", parkingService.entVehicleDetails(idparking));
		return response;
	}
	
	@GetMapping(path="/parkings/vehiclesact",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<ParkingVehicleDTO>> getParkings(HttpServletRequest request) {
		Map<String, List<ParkingVehicleDTO>> response = new HashMap<>();
		response.put("parkings", userService.findAllVehiclesInParkingsInd(dataAccessFilter.getUserIdToken(request)));
		
		return response;
	}
	
	@GetMapping(path="/parkings/{idparking}/vehiclesrank",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<VehicleRankDTO>> getVehiclesRank(@PathVariable Long idparking,HttpServletRequest request) {//dataAccessFilter.parkingAccessIdFilter(request,id);
		dataAccessFilter.accessParkingPartnerFilter(request, idparking);
		Map<String, List<VehicleRankDTO>> response = new HashMap<>();
		response.put("vehicles",parkingService.rankVehicles(idparking));
		return response;
	}
	
	@GetMapping(path="/parkings/vehiclesfirst",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<Vehicle>> getVehicleFistTimeByUser(HttpServletRequest request) {
		Map<String, List<Vehicle>> response = new HashMap<>();
		response.put("vehicles",parkingService.vehiclesFirstTimeByUser(dataAccessFilter.getUserIdToken(request)));
		
		return response;
	}
	
	
	@GetMapping(path="/parkings/vehiclesrep",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<Vehicle>> getVehicleRepeatedlyByUser(HttpServletRequest request) {
		Map<String, List<Vehicle>> response = new HashMap<>();
		response.put("vehicles",parkingService.vehiclesRepeatedlyByUser(dataAccessFilter.getUserIdToken(request)));
		return response;
	}
	//-----------------------------------------END PARKINGS URI'S-------------------------------------------------------
	
	
	
	//-------------------------------------------USERS URI'S------------------------------------------------------------
	
	@PostMapping("/users")
	  public ResponseEntity<UserDTO> registerUsuario(
	      @Valid @RequestBody RegisterRequest request
	  ) {
	    return ResponseEntity.ok(service.register(request,Role.USUARIO));
	  }
	
	//-----------------------------------------END USERS URI'S----------------------------------------------------------
}
