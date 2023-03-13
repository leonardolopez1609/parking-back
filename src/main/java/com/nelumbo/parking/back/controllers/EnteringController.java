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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nelumbo.parking.back.entities.Entering;
import com.nelumbo.parking.back.services.IEnteringService;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/enterings")
@CrossOrigin(origins = { "*" })
public class EnteringController {

	@Autowired
	private IEnteringService enteringService;

	// Revisado
	@GetMapping(path = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Entering getEntering(@PathVariable Long id) {

		return enteringService.findById(id).get();

	}

	// Revisado -- @Pattern(regexp = "^[a-zA-Z0-9]", message = "length must be 3") @Max(6)
	@PostMapping(path = "/vehicle/{plate}/in/parking/{idparking}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Map<String, Object> createEntering(@PathVariable Long idparking,
			 @PathVariable @Min(6) String plate) {
		
		Map<String, Object> response = new HashMap<>();

		response.put("id", enteringService.create(idparking, plate).getIdentering());

		return response;

	}

}
