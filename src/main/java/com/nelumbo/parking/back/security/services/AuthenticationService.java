package com.nelumbo.parking.back.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nelumbo.parking.back.entities.Role;
import com.nelumbo.parking.back.entities.TokenType;
import com.nelumbo.parking.back.entities.User;
import com.nelumbo.parking.back.exceptions.RequestException;
import com.nelumbo.parking.back.security.entities.AuthenticationRequest;
import com.nelumbo.parking.back.security.entities.AuthenticationResponse;
import com.nelumbo.parking.back.security.entities.RegisterRequest;
import com.nelumbo.parking.back.services.IUserService;

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
	  
	  
	  
	  public AuthenticationResponse register(RegisterRequest request) {
		   //Construir el usuario a partir del request(Usuario mapeado)
			  User user = User.builder()
				.name(request.getName())
		        .email(request.getEmail())
		        .password(passwordEncoder.encode(request.getPassword()))
		        .role(Role.SOCIO)
		        .build();
			  
			  //llamar al servicio de guardar token
		    var savedUser = userService.save(user);
		    
		    var jwtToken = jwtService.generateToken(user);
		  //  saveUserToken(savedUser, jwtToken);
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

		  /**private void saveUserToken(User user, String jwtToken) {
		    var token = Token.builder()
		        .user(user)
		        .token(jwtToken)
		        .tokenType(TokenType.BEARER)
		        .expired(false)
		        .revoked(false)
		        .build();
		    //llamar al servicio de token
		    tokenService.save(token);
		  }**/

		 /** private void revokeAllUserTokens(User user) {
		    var validUserTokens = tokenService.findAllValidTokenByUser(user.getIduser());
		    if (validUserTokens.isEmpty())
		      return;
		    validUserTokens.forEach(token -> {
		      token.setExpired(true);
		      token.setRevoked(true);
		    });
		    //llamar al serrvicio de token
		    tokenService.saveAll(validUserTokens);
		  }**/
	
}
