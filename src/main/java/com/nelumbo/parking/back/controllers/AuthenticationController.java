package com.nelumbo.parking.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nelumbo.parking.back.security.entities.AuthenticationRequest;
import com.nelumbo.parking.back.security.entities.AuthenticationResponse;
import com.nelumbo.parking.back.security.entities.RegisterRequest;
import com.nelumbo.parking.back.security.services.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private  AuthenticationService service;
	

	  @PostMapping("/register")
	  public ResponseEntity<AuthenticationResponse> register(
	      @Valid @RequestBody RegisterRequest request
	  ) {
	    return ResponseEntity.ok(service.register(request));
	  }
	  @PostMapping("/authenticate")
	  public ResponseEntity<AuthenticationResponse> authenticate(
			  @Valid @RequestBody AuthenticationRequest request
	  ) {
	    return ResponseEntity.ok(service.authenticate(request));
	  }

}
