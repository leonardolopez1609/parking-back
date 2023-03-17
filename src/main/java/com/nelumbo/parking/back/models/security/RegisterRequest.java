package com.nelumbo.parking.back.models.security;


import com.nelumbo.parking.back.customvalidators.UniqueEmail;
import com.nelumbo.parking.back.customvalidators.UniqueUserName;
import jakarta.validation.constraints.Email;
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
	  @UniqueUserName
	  private String name;
	  
      
      @NotBlank(message = "El email es requerido")
      @Email(message = "El email no tiene el formato correcto")
      @UniqueEmail
	  private String email;
	  
	 
	  @NotBlank(message = "La contraseña es requerida")
	  private String password;
}
