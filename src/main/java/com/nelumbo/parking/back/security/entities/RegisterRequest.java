package com.nelumbo.parking.back.security.entities;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
	  @NotBlank(message = "El nombre es requerido")
	  private String name;
	  
      
      @NotBlank(message = "El email es requerido")
	  private String email;
	  
	 
	  @NotBlank(message = "La contraseña es requerida")
	  private String password;
}
