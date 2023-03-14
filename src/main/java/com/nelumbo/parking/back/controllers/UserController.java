package com.nelumbo.parking.back.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	public Map<String, Object> getParkings(@PathVariable Long id,HttpServletRequest request) {
		dataAccessFilter.userAccessIdFilter(request, id);
		Map<String, Object> response = new HashMap<>();
		response.put("parkings", userService.findAllVehiclesInParkingsInd(id));
		
		return response;
	}
	
	
	/**
	//Revisado
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Map<String, Object> createUser(@Valid @RequestBody User user) {

		Map<String, Object> response = new HashMap<>();

		response.put("user", userService.create(user));
		response.put("mensaje", "Usuario creado con éxito!");

		return response;
	}**/
	
	//Revisado
	@PostMapping(path="/email",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Map<String, Object> sendEmail(@Valid @RequestBody EmailContentDTO emailContent) {

		Map<String, Object> response = userService.sendEmail(emailContent);
		return response;
	}
    
	@PutMapping(path="/{iduser}/associate/partner/{idpartner}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> asignateParking(@PathVariable Long idpartner, @PathVariable Long iduser,HttpServletRequest request) {
       
		dataAccessFilter.userAccessIdFilter(request, idpartner);
		userService.associateUser(idpartner, iduser);
		Map<String, Object> response = new HashMap<>();
		response.put("mensaje", "Usuario asociado con éxito");

		return response;

	}
	
	
}
