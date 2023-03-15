package com.nelumbo.parking.back.config.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint  implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		     response.addHeader("access_denied_reason", "authentication_required");
		     //log.atError().log("ACCESO DENEGADO");
		        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		        response.getWriter().write("Token no valido");
		        response.setStatus(403);
		        response.getWriter().flush();
	        
	        
		
	}

}
