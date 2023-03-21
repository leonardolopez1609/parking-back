package com.nelumbo.parking.back.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.nelumbo.parking.back.models.dto.ParkingDTO;
import com.nelumbo.parking.back.models.dto.TextResponseDTO;
import com.nelumbo.parking.back.models.dto.UserDTO;
import com.nelumbo.parking.back.models.dto.VehicleEntDTO;
import com.nelumbo.parking.back.models.dto.VehicleHistoryDTO;
import com.nelumbo.parking.back.models.dto.VehicleRankDTO;
import com.nelumbo.parking.back.models.entities.Parking;
import com.nelumbo.parking.back.models.entities.Role;
import com.nelumbo.parking.back.models.entities.Vehicle;
import com.nelumbo.parking.back.models.security.AuthenticationResponse;
import com.nelumbo.parking.back.models.security.RegisterRequest;
import com.nelumbo.parking.back.services.business.IHistoryService;
import com.nelumbo.parking.back.services.business.IParkingService;
import com.nelumbo.parking.back.services.business.IUserService;
import com.nelumbo.parking.back.services.security.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = { "*" })
public class AdminsController {
	
	//--------------SERVICES----------------
	@Autowired
	private  AuthenticationService service;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IParkingService parkingService;
	
	@Autowired
	private IHistoryService historyService;
	
	//------------END SERVICES--------------
	
	//---------------------------------------------PARTNERS URI'S-------------------------------------------------------
	
	@PostMapping(path="/partners",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<UserDTO> registerPartner(
	      @Valid @RequestBody RegisterRequest request
	  ) {
	    return ResponseEntity.ok(service.register(request,Role.SOCIO));
	  }
	
	
	
	@PutMapping(path="/partners/{idpartner}/associate/users/{iduser}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public TextResponseDTO asignatePartner(@PathVariable Long idpartner, @PathVariable Long iduser,HttpServletRequest request) { 
       // dataAccessFilter.userPartnerAccessIdFilter(request, idpartner);
		userService.associateUser(idpartner, iduser);
		return new TextResponseDTO("Usuario asociado con éxito");
	}
	
	
	@PutMapping(path="/partners/{idpartner}/associate/parkings/{idparking}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public TextResponseDTO asignateParking(@PathVariable Long idparking, @PathVariable Long idpartner,HttpServletRequest request) {
		userService.associateParking(idpartner, idparking);
		return new TextResponseDTO("Parqueadero asociado con éxito");
	}
	
	@GetMapping(path="/partners/{idpartner}/users",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<UserDTO>> getAllUsersByPartner(@PathVariable Long idpartner) {
		Map<String, List<UserDTO>> response = new HashMap<>();
		response.put("usuarios", userService.findAllUsersByPartnerInd(idpartner));
		return response;
	}
	
	//--------------------------------------------END PARTNERS URI'S--------------------------------------------------------
	
	
	//-----------------------------------------------PARKINGS URI'S-------------------------------------------------------
	
	@GetMapping(path="/parkings/{idparking}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ParkingDTO getParking(@PathVariable Long idparking,HttpServletRequest request) {		
		//dataAccessFilter.parkingAccessIdFilter(request,id);
		return parkingService.findDTOByID(idparking);	
	}
	
	@GetMapping(path="/parkings",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<ParkingDTO>> getParkings(HttpServletRequest request) {
		Map<String, List<ParkingDTO>> response = new HashMap<>();
		response.put("parkings", parkingService.findAll());
		return response;
	}
	
	@PostMapping(path="/parkings",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Parking createParking(@Valid @RequestBody ParkingDTO parking) {
		return parkingService.create(parking);
	}
	
	@DeleteMapping(path="/parkings/{idparking}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public TextResponseDTO deleteParking(@PathVariable Long idparking,HttpServletRequest request) {
		parkingService.delete(idparking);
		return new TextResponseDTO("Parqueadero eliminado con éxito!");
	}
	
	
	@GetMapping(path="/parkings/vehiclesfirst",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<Vehicle>> getVehicleFirstTime() {
		Map<String, List<Vehicle>> response = new HashMap<>();
		response.put("vehicles",parkingService.vehiclesFirstTime());
		return response;
	}
	
	@GetMapping(path="/parkings/vehiclesrep",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<Vehicle>> getVehicleRepeatedly() {
       Map<String, List<Vehicle>> response = new HashMap<>();
		
		response.put("vehicles",parkingService.vehiclesRepeatedly());
		
		return response;
	}
	
	@GetMapping(path="/parkings/{idparking}/vehicles/datemin/{dateMin}/datemax/{dateMax}/plate/{plate}",
			consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public  Map<String, List<VehicleHistoryDTO>> getVehiclesByRangeDateAndPlate(@PathVariable String dateMin,@PathVariable String dateMax,
			@PathVariable Long idparking,@PathVariable String plate,HttpServletRequest request) throws ParseException {
		//dataAccessFilter.parkingAccessIdFilter(request, id);
		Map<String, List<VehicleHistoryDTO>> response = new HashMap<>();	
		Date dateMin2 = historyService.parseDate(dateMin);
		Date dateMax2 = historyService.parseDate(dateMax);
		response.put("vehicles", historyService.getHistoryByRangeDateAndPlate(
				dateMin2,
				dateMax2,
				idparking,plate));		
		return response;
	}
	
	@GetMapping(path="/parkings/{idparking}/vehiclesact",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<VehicleEntDTO>> getVehiclesAct(@PathVariable Long idparking,HttpServletRequest request) {
		
		//dataAccessFilter.parkingAccessIdFilter(request,id);
		Map<String, List<VehicleEntDTO>> response = new HashMap<>();
	
		response.put("vehicles", parkingService.entVehicleDetails(idparking));
		return response;
	}
	
	@GetMapping(path="/parkings/{idparking}/vehiclesrank",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<VehicleRankDTO>> getVehiclesRank(@PathVariable Long idparking,HttpServletRequest request) {
		//dataAccessFilter.parkingAccessIdFilter(request,id);
		Map<String, List<VehicleRankDTO>> response = new HashMap<>();
			response.put("vehicles",parkingService.rankVehicles(idparking));
			
			return response;
	}
	
	//--------------------------------------------END PATKINGS URI'S------------------------------------------------------

	//-------------------------------------------USERS URI'S--------------------------------------------------------------
	
	@PostMapping("/users")
	  public ResponseEntity<UserDTO> registerUsuario(
	      @Valid @RequestBody RegisterRequest request
	  ) {
	    return ResponseEntity.ok(service.register(request,Role.USUARIO));
	  }
	
	//------------------------------------------END USERS URI'S----------------------------------------------------------
}
