package com.nelumbo.parking.back.config.security;

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
import com.nelumbo.parking.back.models.entities.Role;


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
		        .requestMatchers("/auth/authenticate/**","/swagger-ui/**","/v3/api-docs/swagger-config/**", "/v3/api-docs/**")
		        .permitAll()
		        .and()
		        .authorizeHttpRequests()
		        .requestMatchers("/admins/**")
		        .hasAuthority(Role.ADMIN.toString())
		        .and()
		        .authorizeHttpRequests()
		        .requestMatchers("/partners/**")
		        .hasAuthority(Role.SOCIO.toString())
		        .and()
		        .authorizeHttpRequests()
		        .requestMatchers("/users/**")
		        .hasAuthority(Role.USUARIO.toString())
		        .and()
		        .authorizeHttpRequests()
		        .requestMatchers("/emails/**")
		        .hasAnyAuthority(Role.ADMIN.toString(),Role.SOCIO.toString())
		        .and().exceptionHandling()
		        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
		        .accessDeniedHandler(new CustomAccessDeniedHandler())
		        .and()
		        .sessionManagement()
		        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		        .and()
		        .authenticationProvider(authenticationProvider)
		        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
		        .logout()
		        .logoutUrl("/auth/logout")
		        .addLogoutHandler(logoutHandler)
		        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
			
			   
				  return http.build();
	   
	  }
	  
	
}
