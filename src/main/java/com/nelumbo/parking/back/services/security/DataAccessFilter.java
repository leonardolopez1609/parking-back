package com.nelumbo.parking.back.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.models.entities.Parking;
import com.nelumbo.parking.back.models.entities.User;
import com.nelumbo.parking.back.services.business.IParkingService;
import com.nelumbo.parking.back.services.business.IUserService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DataAccessFilter {

	@Autowired
	private IParkingService parkingService;
	
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	
	public void parkingAccessIdFilter(HttpServletRequest request,Long id) {
		 final String authHeader = request.getHeader("Authorization");
		 final String jwt;
		    
		jwt = authHeader.substring(7);
		Parking p = parkingService.findById(id);    
       if(!p.getUser().getEmail().equals(jwtService.extractUsername(jwt))) {
       	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
       }
		
	}
	
	public void userAccessIdFilter(HttpServletRequest request, Long id) {
		 final String authHeader = request.getHeader("Authorization");
		 final String jwt;
		    
		jwt = authHeader.substring(7);
		User u = userService.findById(id);    
       if(!u.getEmail().equals(jwtService.extractUsername(jwt))) {
       	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
       }
		
	}
	
}
