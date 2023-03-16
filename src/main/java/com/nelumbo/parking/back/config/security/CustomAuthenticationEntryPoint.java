package com.nelumbo.parking.back.config.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nelumbo.parking.back.models.dto.TextResponseDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint  implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		 
		TextResponseDTO t = new TextResponseDTO("Token Inválido");
		ObjectMapper mapper = new ObjectMapper();
		 response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		 response.getWriter().print(mapper.writeValueAsString(t));
         response.setStatus(403);
		
	}

}
