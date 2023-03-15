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
import com.nelumbo.parking.back.models.dto.EnteringDTO;
import com.nelumbo.parking.back.services.business.IEnteringService;
import com.nelumbo.parking.back.services.security.DataAccessFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/enterings")
@CrossOrigin(origins = { "*" })
public class EnteringController {

	@Autowired
	private IEnteringService enteringService;
	
	@Autowired
	private DataAccessFilter dataAccessFilter;

	// Revisado
	@GetMapping(path = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public EnteringDTO getEntering(@PathVariable Long id,HttpServletRequest request) {
        dataAccessFilter.enteringAccessIdFilter(request, id);
		return enteringService.findDTOById(id);

	}

	// Revisado -- @Pattern(regexp = "^[a-zA-Z0-9]", message = "length must be 3") @Max(6)
	@PostMapping(path = "/vehicle/{plate}/in/parking/{idparking}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Map<String, Long> createEntering(@PathVariable Long idparking,
			 @PathVariable @Min(3) String plate,HttpServletRequest request) {
		
		dataAccessFilter.parkingAccessIdFilter(request, idparking);
		Map<String, Long> response = new HashMap<>();

		response.put("id", enteringService.create(idparking, plate).getIdentering());

		return response;

	}

}
