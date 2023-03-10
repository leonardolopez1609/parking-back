package com.nelumbo.parking.back.security.entities;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
	  @NotNull(message = "El nombre es requerido")
	  @NotBlank(message = "El nombre es requerido")
	  private String name;
	  
      @NotNull(message = "El email es requerido")
      @NotBlank(message = "El email es requerido")
	  private String email;
	  
	 
	  @NotNull(message = "La contraseña es requerida")
	  @NotBlank(message = "La contraseña es requerida")
	  private String password;
}
