package com.nelumbo.parking.back.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nelumbo.parking.back.models.dto.EnteringDTO;
import com.nelumbo.parking.back.models.dto.HistoryDTO;
import com.nelumbo.parking.back.models.dto.ParkingDTO;
import com.nelumbo.parking.back.models.dto.ParkingVehicleDTO;
import com.nelumbo.parking.back.models.dto.TextResponseDTO;
import com.nelumbo.parking.back.models.dto.VehicleEntDTO;
import com.nelumbo.parking.back.services.business.IEnteringService;
import com.nelumbo.parking.back.services.business.IHistoryService;
import com.nelumbo.parking.back.services.business.IParkingService;
import com.nelumbo.parking.back.services.business.IUserService;
import com.nelumbo.parking.back.services.security.DataAccessFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = { "*" })
public class UsersController {
	
	//--------------SERVICES----------------
	@Autowired
	private IEnteringService enteringService;
	
	@Autowired
	private DataAccessFilter dataAccessFilter;
	
	@Autowired
	private IHistoryService historyService;
	
	@Autowired
	private IParkingService parkingService;
	
	@Autowired
	private IUserService userService;
	
	//------------END SERVICES--------------
	
	
	
	//-----------------------------------------------PARKINGS URI'S-------------------------------------------------------
	
	@GetMapping(path="/parkings/{idparking}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ParkingDTO getParking(@PathVariable Long idparking,HttpServletRequest request) {		
		dataAccessFilter.accessParkingUserFilter(request,idparking);
		return parkingService.findDTOByID(idparking);	
	}
	
	@GetMapping(path="/parkings/{idparking}/vehiclesact",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<VehicleEntDTO>> getVehiclesAct(@PathVariable Long idparking,HttpServletRequest request) {
		dataAccessFilter.accessParkingUserFilter(request,idparking);
		Map<String, List<VehicleEntDTO>> response = new HashMap<>();	
		response.put("vehicles", parkingService.entVehicleDetails(idparking));
		return response;
	}
	
	@GetMapping(path="/parkings/vehiclesact",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<ParkingVehicleDTO>> getParkings(HttpServletRequest request) {
		Map<String, List<ParkingVehicleDTO>> response = new HashMap<>();
		response.put("parkings", userService.findAllVehiclesInParkingsInd(dataAccessFilter.getUserIdPartnerToken(request)));
		
		return response;
	}
	
	@GetMapping(path="parkings/vehicles/{idvehicle}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public VehicleEntDTO getVehicleAct(@PathVariable Long idvehicle,HttpServletRequest request) {
		dataAccessFilter.accessParkingUserAndIdVehicle(request,idvehicle);
		return parkingService.vehicleById(idvehicle);
	}
	
	//--------------------------------------------END PATKINGS URI'S------------------------------------------------------
	
	//------------------------------------------ENTERINGS URI'S---------------------------------------------------------
	
	@GetMapping(path = "/enterings/{identering}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public EnteringDTO getEntering(@PathVariable Long identering,HttpServletRequest request) {
        dataAccessFilter.enteringAccessIdFilter(request, identering);
		return enteringService.findDTOById(identering);
	}
	
	@PostMapping(path = "/enterings/vehicles/{plate}/in/parking/{idparking}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Map<String, Long> createEntering(@PathVariable Long idparking,
			 @PathVariable @Min(3) String plate,HttpServletRequest request) {	
		dataAccessFilter.accessParkingUserFilter(request,idparking);
		Map<String, Long> response = new HashMap<>();
		response.put("id", enteringService.create(idparking, plate).getIdentering());
		return response;
	}
	//----------------------------------------END ENTERINGS URI'S-------------------------------------------------------
	
	//------------------------------------------HISTORIES URI'S---------------------------------------------------------
	
	@GetMapping(path="/histories/{idhistory}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public HistoryDTO getHistory(@PathVariable Long idhistory,HttpServletRequest request) {
		dataAccessFilter.historyAccessIdFilter(request, idhistory);
		return historyService.findDTOById(idhistory);
	}
	
	@PostMapping(path="/histories/departure/vehicles/{plate}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public  TextResponseDTO createHistory(@Valid @PathVariable String plate,HttpServletRequest request) {		
		dataAccessFilter.accessParkingUserAndPlate(request,plate);
		historyService.create(plate);
		return new TextResponseDTO ("Salida registrada");
	}
	
	//----------------------------------------END HISTORIES URI'S-------------------------------------------------------

}
