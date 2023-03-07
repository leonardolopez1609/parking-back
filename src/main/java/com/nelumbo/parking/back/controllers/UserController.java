package com.nelumbo.parking.back.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nelumbo.parking.back.entities.User;
import com.nelumbo.parking.back.services.IUserService;

import jakarta.validation.Valid;

import com.nelumbo.parking.back.DTO.EmailContentDTO;
import com.nelumbo.parking.back.DTO.ParkingVehicleDTO;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = { "*" })
public class UserController {

	@Autowired
	private IUserService userService;

	//Revisado
	@GetMapping(path="/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public User getUser(@PathVariable Long id) {

		return userService.findById(id).get();
	}


	//Revisado
	@GetMapping(path="/{id}/parkings",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public List<ParkingVehicleDTO> getParkings(@PathVariable Long id) {

		return userService.findAllParkingsInd(id);
	}

	
	//Revisado
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Map<String, Object> createUser(@Valid @RequestBody User user) {

		Map<String, Object> response = new HashMap<>();

		response.put("user", userService.create(user));
		response.put("mensaje", "Usuario creado con éxito!");

		return response;
	}
	
	//Por implementar
	@PostMapping(path="/email",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Map<String, Object> sendEmail(@Valid @RequestBody EmailContentDTO emailContent) {

		Map<String, Object> response = new HashMap<>();

		response.put("mensaje", userService.sendEmail(emailContent));

		return response;
	}
    
	
	//Revisado
	@PutMapping(path="/{iduser}/associate/parkings/{idparking}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String, Object> asignateParking(@PathVariable Long idparking, @PathVariable Long iduser) {
       
		userService.associateParking(iduser, idparking);
		Map<String, Object> response = new HashMap<>();
		response.put("mensaje", "Parqueadero asociado con éxito!");

		return response;

	}
}
