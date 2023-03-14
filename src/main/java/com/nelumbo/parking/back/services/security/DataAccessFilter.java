package com.nelumbo.parking.back.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.models.entities.Entering;
import com.nelumbo.parking.back.models.entities.History;
import com.nelumbo.parking.back.models.entities.Parking;
import com.nelumbo.parking.back.models.entities.Role;
import com.nelumbo.parking.back.models.entities.User;
import com.nelumbo.parking.back.services.business.IEnteringService;
import com.nelumbo.parking.back.services.business.IHistoryService;
import com.nelumbo.parking.back.services.business.IParkingService;
import com.nelumbo.parking.back.services.business.IUserService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DataAccessFilter {

	@Autowired
	private IParkingService parkingService;
	
	@Autowired
	private IHistoryService historyService;
	
	@Autowired
	private IEnteringService enteringService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	//permitir acceso a Usuario
	public void parkingAccessIdFilter(HttpServletRequest request,Long id) {
		 final String authHeader = request.getHeader("Authorization");
		 final String jwt;
		    
		jwt = authHeader.substring(7);
		
		User u= userService.findByEmail(jwtService.extractUsername(jwt)).get();
	   Parking p = parkingService.findById(id);
	   
       if(u.getRole()!=Role.ADMIN&&!(p.getUser().equals(u)||p.getUser().equals(u.getUser()))) {
       	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
       }
		
	}
	
	
	
	public void userAccessIdFilter(HttpServletRequest request, Long id) {
		 final String authHeader = request.getHeader("Authorization");
		 final String jwt;
		    
		jwt = authHeader.substring(7);
		User u = userService.findById(id);
		User ut= userService.findByEmail(jwtService.extractUsername(jwt)).get();
       if(ut.getRole()!=Role.ADMIN &&!u.equals(ut)) {
       	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
       }
	}
	
	public void parkingAccessIdUserFilter(HttpServletRequest request, Long iduser) {
		 final String authHeader = request.getHeader("Authorization");
		 final String jwt;
		    
		jwt = authHeader.substring(7);
		User user=userService.findById(iduser); 
		User userJwt = userService.findByEmail(jwtService.extractUsername(jwt)).get();
       if(userJwt.getRole()!=Role.ADMIN&&(!user.equals(userJwt))) {
       	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
       }
		
	}
	
	
	//permitir acceso usuario
	public void historyAccessIdFilter(HttpServletRequest request, Long id) {
		 final String authHeader = request.getHeader("Authorization");
		 final String jwt;
		    
		jwt = authHeader.substring(7);
		History h = historyService.findById(id);    
		User ut= userService.findByEmail(jwtService.extractUsername(jwt)).get();
      if(ut.getRole()!=Role.ADMIN &&!(h.getParking().getUser().equals(ut)||ut.getUser().equals(h.getParking().getUser()) )) {
      	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
      }
	}
	
	
	//permitir acceso usuario
	public void enteringAccessIdFilter(HttpServletRequest request, Long id) {
		 final String authHeader = request.getHeader("Authorization");
		 final String jwt;
		    
		jwt = authHeader.substring(7);
		Entering e = enteringService.findById(id);
		User ut= userService.findByEmail(jwtService.extractUsername(jwt)).get();
     if(ut.getRole()!=Role.ADMIN &&!(e.getParking().getUser().equals(ut)||ut.getUser().equals(e.getParking().getUser()))) {
     	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
     }
	}



	public void parkingAccessIdAndPlateFilter(HttpServletRequest request, String plate) {
		 final String authHeader = request.getHeader("Authorization");
		 final String jwt;
		    
		jwt = authHeader.substring(7);
		
		User u= userService.findByEmail(jwtService.extractUsername(jwt)).get();
	    Entering e = enteringService.findOneByPlate(plate);
	   
       if(u.getRole()!=Role.ADMIN&&!(e.getParking().getUser().equals(u)||u.getUser().equals(e.getParking().getUser()))) {
       	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
       }
		
	}
}
