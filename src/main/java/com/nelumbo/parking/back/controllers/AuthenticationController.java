package com.nelumbo.parking.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nelumbo.parking.back.models.dto.UserDTO;
import com.nelumbo.parking.back.models.entities.Role;
import com.nelumbo.parking.back.models.security.AuthenticationRequest;
import com.nelumbo.parking.back.models.security.AuthenticationResponse;
import com.nelumbo.parking.back.models.security.RegisterRequest;
import com.nelumbo.parking.back.services.security.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private  AuthenticationService service;
	
	  
	  @PostMapping("admin/register")
	  public ResponseEntity<UserDTO> registerAdmin(
	      @Valid @RequestBody RegisterRequest request
	  ) {
	    return ResponseEntity.ok(service.register(request,Role.ADMIN));
	  }
	 
	  
	  @PostMapping("/authenticate")
	  public ResponseEntity<AuthenticationResponse> authenticate(
			  @Valid @RequestBody AuthenticationRequest request
	  ) {
	    return ResponseEntity.ok(service.authenticate(request));
	  }

}
