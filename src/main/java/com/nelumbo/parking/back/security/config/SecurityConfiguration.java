package com.nelumbo.parking.back.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.nelumbo.parking.back.entities.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
      @Autowired
	  private JwtAuthenticationFilter jwtAuthFilter;
      
      @Autowired
	  private  AuthenticationProvider authenticationProvider;
      
      @Autowired
	  private LogoutHandler logoutHandler;

	  
	  @Bean
	  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf()
	        .disable()
	        .authorizeHttpRequests()
	        .requestMatchers("/auth/**")
	         .permitAll()
	        //.anyRequest()
	       // .authenticated()
	        .and()
	        .authorizeHttpRequests()
	        .requestMatchers("/parkings/**")
	        .hasAuthority(Role.SOCIO.toString())	        
	        .and()
	        .authorizeHttpRequests()
	        .requestMatchers("/users/**")
	        .hasAuthority(Role.SOCIO.toString())
	        .and()
	        .authorizeHttpRequests()
	        .requestMatchers("/enterings/**")
	        .hasAuthority(Role.SOCIO.toString())
	        .and()
	        .sessionManagement()
	        //revisar
	         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        //Revisar
	        .authenticationProvider(authenticationProvider)
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
	        .logout()
	        .logoutUrl("/auth/logout")
	        .addLogoutHandler(logoutHandler)
	        //---------------------------------------------------------------------------------Revisar
	        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
	    ;

	    return http.build();
	  }
}
