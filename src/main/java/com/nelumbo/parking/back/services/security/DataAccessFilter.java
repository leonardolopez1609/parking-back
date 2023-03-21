package com.nelumbo.parking.back.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.exceptions.RequestException;
import com.nelumbo.parking.back.models.entities.Entering;
import com.nelumbo.parking.back.models.entities.History;
import com.nelumbo.parking.back.models.entities.Parking;
import com.nelumbo.parking.back.models.entities.User;
import com.nelumbo.parking.back.services.business.IEnteringService;
import com.nelumbo.parking.back.services.business.IHistoryService;
import com.nelumbo.parking.back.services.business.IParkingService;
import com.nelumbo.parking.back.services.business.IVehicleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Service
public class DataAccessFilter {

	@Autowired
	private IParkingService parkingService;
	
	@Autowired
	private IHistoryService historyService;
	
	@Autowired
	private IEnteringService enteringService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private IVehicleService vehicleService;
	
	
	
	User getUserToken(HttpServletRequest request) {
		 return jwtService.getUserToken(request);
		
	}
	
	public Long getUserIdPartnerToken(HttpServletRequest request) {
		 User u =jwtService.getUserToken(request).getUser();
	     if(u==null) {
		  throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
	      }
	     Long idPartner =u.getIduser();
		return idPartner;
	}
	
	public Long getUserIdToken(HttpServletRequest request) {
		return jwtService.getUserToken(request).getIduser();
	}
	
	
	
	
	//VALIDAR QUE EL HISTORIAL PERTENEZCA AL SOCIO ASOCIADO AL USUARIO
	public void historyAccessIdFilter(HttpServletRequest request, Long id) {
		 
		History h = historyService.findById(id);    
		User ut= jwtService.getUserToken(request);
		if(h==null) {
			throw new RequestException("No existe el registro de salida con ID: "+id);
		}
		
		if(!(h.getParking().getUser().equals(ut.getUser()) )) {
      	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
      }
	}
	
	
	//VALIDAR QUE EL REGISTRO DE ENTRADA PERTENEZCA AL SOCIO ASOCIADO AL USUARIO
	public void enteringAccessIdFilter(HttpServletRequest request, Long id) {
		 
		Entering e = enteringService.findById(id);
		User ut= jwtService.getUserToken(request);
		
		if(e==null) {
			throw new RequestException("No existe el registro de entrada con ID: "+id);
		}
     if(!(e.getParking().getUser().equals(ut.getUser()))) {
     	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
     }
	}


	
    //VALIDAR QUE EL PARKING PERTENEZCA AL SOCIO ASOCIADO AL USUARIO
	public void accessParkingUserFilter(HttpServletRequest request, Long id) {
		User ut = jwtService.getUserToken(request);
		Parking p =parkingService.findById(id);
		User partner=p.getUser();
		
		 if(partner==null||!(partner).equals(ut.getUser())) {
		       	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
		       }
		
	}

 //VALIDAR QUE EL REGISTRO DE ENTRADA POR PLACA PERTENEZA AL SOCIO ASOCIADO AL USUARIO
	public void accessParkingUserAndPlate(HttpServletRequest request, @Valid String plate) {
        vehicleService.invalidPlate(plate);		
		User ut= jwtService.getUserToken(request);
	    
	    	Entering e = enteringService.findOneByPlate(plate);
	    	User partner =ut.getUser();
	 	   
	        if(!(e.getParking().getUser().equals(partner))) {
	        	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
	        }
	    
	}

	public void accessParkingUserAndIdVehicle(HttpServletRequest request, long id) {
		vehicleService.findById(id);
		Entering e = enteringService.findByIdVehicle(id);
		  User u = jwtService.getUserToken(request).getUser();
		  if(!(e.getParking().getUser().equals(u))) {
		       	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
		       }
		  
		
	}

	public void accessParkingPartnerFilter(HttpServletRequest request, Long id) {
		User ut = jwtService.getUserToken(request);
		Parking p =parkingService.findById(id);
		
		
		 if(!(ut.equals(p.getUser()))) {
		       	throw new BusinessException(HttpStatus.FORBIDDEN, "Acceso no autorizado");
		       }
		
	}

}
