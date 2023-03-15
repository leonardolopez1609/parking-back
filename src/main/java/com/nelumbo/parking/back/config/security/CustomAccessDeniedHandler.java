package com.nelumbo.parking.back.config.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.nelumbo.parking.back.models.dto.TextResponseDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		    response.addHeader("access_denied_reason", "not_authorized");
	        log.atError().log("ACCESO DENEGADO");
	        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	        response.getWriter().write("Acceso Denegado");
	        response.setStatus(403);
	        response.getWriter().flush();
		    
		    
		    return;
		    
	}

}
