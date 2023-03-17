package com.nelumbo.parking.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nelumbo.parking.back.models.dto.EmailContentDTO;
import com.nelumbo.parking.back.models.dto.TextResponseDTO;
import com.nelumbo.parking.back.services.business.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/emails")
@CrossOrigin(origins = { "*" })
public class EmailController {

	
	//--------------SERVICES----------------
	
	@Autowired
	private IUserService userService;
	
	//------------END SERVICES--------------
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public TextResponseDTO sendEmail(@Valid @RequestBody EmailContentDTO emailContent) {

		return userService.sendEmail(emailContent);
	}
	
}
