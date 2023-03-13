package com.nelumbo.parking.back.security.services;

import org.springframework.security.web.authentication.logout.LogoutHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class LogoutService implements LogoutHandler{

	//llamar al servicio de token
	 

	  @Override
	  public void logout(
	      HttpServletRequest request,
	      HttpServletResponse response,
	      Authentication authentication
	  ) {
	    final String authHeader = request.getHeader("Authorization");
	    final String jwt;
	    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
	      return;
	    }
	    jwt = authHeader.substring(7);
	    
	    //---------No encuentra el token
	  /** Token storedToken = tokenService.findByToken(jwt)
	        .orElse(null);
	    if (storedToken != null) {
	      storedToken.setExpired(true);
	      storedToken.setRevoked(true);
	      
	      //llamar al servicio de token
	      tokenService.save(storedToken);**/
	      SecurityContextHolder.clearContext();
	    //}
	  }
}
