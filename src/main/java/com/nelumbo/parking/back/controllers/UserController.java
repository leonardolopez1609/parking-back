package com.nelumbo.parking.back.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.nelumbo.parking.back.models.dto.EmailContentDTO;
import com.nelumbo.parking.back.models.dto.ParkingVehicleDTO;
import com.nelumbo.parking.back.models.dto.TextResponseDTO;
import com.nelumbo.parking.back.models.dto.UserDTO;
import com.nelumbo.parking.back.services.business.IUserService;
import com.nelumbo.parking.back.services.security.DataAccessFilter;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "*" })
public class UserController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private DataAccessFilter dataAccessFilter;

	//Revisado
	@GetMapping(path="/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDTO getUser(@PathVariable Long id,HttpServletRequest request) {
		dataAccessFilter.userAccessIdFilter(request, id);
		return userService.findDTOById(id);
	}


	//Revisado
	
	@GetMapping(path="/{id}/parkings",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<ParkingVehicleDTO>> getParkings(@PathVariable Long id,HttpServletRequest request) {
		
		dataAccessFilter.userAccessIdFilter(request, id);
		Map<String, List<ParkingVehicleDTO>> response = new HashMap<>();
		response.put("parkings", userService.findAllVehiclesInParkingsInd(id));
		
		return response;
	}
	
	//Revisado
	@PostMapping(path="/email",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public TextResponseDTO sendEmail(@Valid @RequestBody EmailContentDTO emailContent) {

		return userService.sendEmail(emailContent);
	}
    
	
	
	@PutMapping(path="/{iduser}/associate/partner/{idpartner}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public TextResponseDTO asignateParking(@PathVariable Long idpartner, @PathVariable Long iduser,HttpServletRequest request) {
       
        dataAccessFilter.userPartnerAccessIdFilter(request, idpartner);
		userService.associateUser(idpartner, iduser);
		return new TextResponseDTO("Usuario asociado con éxito");

	}
	
	
}
