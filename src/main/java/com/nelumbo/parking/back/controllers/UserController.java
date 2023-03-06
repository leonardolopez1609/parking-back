package com.nelumbo.parking.back.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nelumbo.parking.back.entities.User;
import com.nelumbo.parking.back.services.IUserService;
import com.nelumbo.parking.back.DTO.ParkingVehicleDTO;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = { "*" })
public class UserController {

	@Autowired
	private IUserService userService;

	//Revisado
	@GetMapping("getone/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {

		return new ResponseEntity<User>(userService.findById(id).get(), HttpStatus.OK);
	}

	//Agregar al DTO del vehiculo la fecha de ingreso
	@GetMapping("getparkings/{id}")
	public ResponseEntity<?> getParkings(@PathVariable Long id) {

		return new ResponseEntity<List<ParkingVehicleDTO>>(userService.findAllParkingsInd(id), HttpStatus.OK);
	}

	
	//Revisado
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user) {

		Map<String, Object> response = new HashMap<>();

		response.put("user", userService.create(user));
		response.put("mensaje", "Usuario creado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
    
	
	//Revisado
	@PutMapping("/{iduser}/associate/{idparking}")
	public ResponseEntity<?> asignateParking(@PathVariable Long idparking, @PathVariable Long iduser) {
       
		userService.associateParking(iduser, idparking);
		Map<String, Object> response = new HashMap<>();
		response.put("mensaje", "Parqueadero asociado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}
}
