package com.nelumbo.parking.back.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nelumbo.parking.back.exceptions.RequestException;
import com.nelumbo.parking.back.models.entities.Role;
import com.nelumbo.parking.back.models.entities.TokenType;
import com.nelumbo.parking.back.models.entities.User;
import com.nelumbo.parking.back.models.security.AuthenticationRequest;
import com.nelumbo.parking.back.models.security.AuthenticationResponse;
import com.nelumbo.parking.back.models.security.RegisterRequest;
import com.nelumbo.parking.back.services.business.IUserService;

@Service
public class AuthenticationService {

	  @Autowired
	  private IUserService userService;
	  
	  
	  @Autowired
	  private PasswordEncoder passwordEncoder;
	  
	  @Autowired
	  private JwtService jwtService;
	  
	  @Autowired
	  private AuthenticationManager authenticationManager;	
	  
	  
	  
	  public AuthenticationResponse register(RegisterRequest request, Role role) {
		  
			  User user = User.builder()
				.name(request.getName())
		        .email(request.getEmail())
		        .password(passwordEncoder.encode(request.getPassword()))
		        .role(role)
		        .build();
			
		    var savedUser = userService.save(user);
		    
		    var jwtToken = jwtService.generateToken(user);
		  
		    return AuthenticationResponse.builder()
		        .token(jwtToken)
		        .build();
		  }
	  
	  

		  public AuthenticationResponse authenticate(AuthenticationRequest request) {
		    
			  
			  try {
				  authenticationManager.authenticate(
					        new UsernamePasswordAuthenticationToken(
					            request.getEmail(),
					            request.getPassword()
					        )
					    );
				
			} catch (AuthenticationException e) {
				throw new RequestException("Usuario o contraseña incorrectos");
			}
			  //Capturar error cuando no se pueda autenticar
			  
			  
		    
		    
		    //lamar al servicio de usuario para obtener por email---capturar error
		    var user = userService.findByEmail(request.getEmail())
		        .orElseThrow();
		    var jwtToken = jwtService.generateToken(user);
		   // revokeAllUserTokens(user);
		   // saveUserToken(user, jwtToken);
		    return AuthenticationResponse.builder()
		        .token(jwtToken)
		        .build();
		  }

	
}
