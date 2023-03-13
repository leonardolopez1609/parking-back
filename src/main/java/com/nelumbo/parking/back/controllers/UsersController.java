package com.nelumbo.parking.back.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nelumbo.parking.back.services.business.IUserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = { "*" })
public class UsersController {

	@Autowired
	private IUserService userService;
	
	
	@GetMapping(path="/allparkings",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getAllParkings() {

		Map<String, Object> response = new HashMap<>();
		response.put("parkings", userService.findAllVehiclesInAllParkingsInd());
		
		return response;
	}
	
	//Revisado
		@PutMapping(path="/{iduser}/associate/parkings/{idparking}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
		public Map<String, Object> asignateParking(@PathVariable Long idparking, @PathVariable Long iduser,HttpServletRequest request) {
	       
			
			//dataAccessFilter.userAccessIdFilter(request, iduser);
			userService.associateParking(iduser, idparking);
			Map<String, Object> response = new HashMap<>();
			response.put("mensaje", "Parqueadero asociado con éxito!");

			return response;

		}

	
	
	
}
