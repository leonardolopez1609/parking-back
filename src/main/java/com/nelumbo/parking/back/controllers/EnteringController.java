package com.nelumbo.parking.back.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nelumbo.parking.back.entities.Entering;
import com.nelumbo.parking.back.services.IEnteringService;




	

//agregar la url global /api
@RestController
@RequestMapping("/enterings")
@CrossOrigin(origins = { "*" })
public class EnteringController {

	
	
	
	@Autowired
	private IEnteringService enteringService;

	
	//Ajustar nombres acceso endpoints
	//Utilizar anotaciones para tipo de dato que se recibe y se retorna
	
	//Cambiar el response entity por retornos fijos
	@GetMapping("getone/{id}")
	public ResponseEntity<?> getEntering(@PathVariable Long id) {

		return new ResponseEntity<Entering>(enteringService.findById(id).get(), HttpStatus.OK);

	}

	//Revisado
	@PostMapping("/{plate}/in/{idparking}")
	public ResponseEntity<?> createEntering(@PathVariable Long idparking, @PathVariable String plate) {
		Map<String, Object> response = new HashMap<>();
		
		response.put("id",  enteringService.create(idparking, plate).getIdentering());

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

}
