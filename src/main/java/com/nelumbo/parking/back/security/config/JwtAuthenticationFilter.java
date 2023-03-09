package com.nelumbo.parking.back.security.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.nelumbo.parking.back.security.services.ITokenService;
import com.nelumbo.parking.back.security.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter{
    
	@Autowired
	private  JwtService jwtService;
	
	@Autowired
	private  UserDetailsService userDetailsService;
	
	@Autowired
	private  ITokenService tokenService;
	
	@Override
	  protected void doFilterInternal(
	      @NonNull HttpServletRequest request,
	      @NonNull HttpServletResponse response,
	      @NonNull FilterChain filterChain
	  ) throws ServletException, IOException {
	    final String authHeader = request.getHeader("Authorization");
	    final String jwt;
	    final String userEmail;
	    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
	      filterChain.doFilter(request, response);
	      return;
	    }
	    jwt = authHeader.substring(7);
	    userEmail = jwtService.extractUsername(jwt);
	    //----------------------------------------------Revisar
	    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
	      var isTokenValid = tokenService.findByToken(jwt)
	          .map(t -> !t.isExpired() && !t.isRevoked())
	          .orElse(false);
	      if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
	       //Revisar-------------------------------------------------------------------
	    	  UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	            userDetails,
	            null,
	            userDetails.getAuthorities()
	        );
	    	  //Revisar-----------------------------------------
	        authToken.setDetails(
	            new WebAuthenticationDetailsSource().buildDetails(request)
	        );
	        //Revisar-------------------------------------------------
	        SecurityContextHolder.getContext().setAuthentication(authToken);
	      }
	    }
	    filterChain.doFilter(request, response);
	  }

}
