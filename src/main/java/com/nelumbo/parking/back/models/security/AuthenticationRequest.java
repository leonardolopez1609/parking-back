package com.nelumbo.parking.back.models.security;

import jakarta.validation.constraints.Email;
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
public class AuthenticationRequest {

	@NotNull(message = "El email es requerido")
    @NotBlank(message = "El email es requerido")
	@Email(message = "El email no tiene el formato correcto")
	private String email;
	
	 @NotNull(message = "La contraseña es requerida")
	 @NotBlank(message = "La contraseña es requerida")
	 String password;	
}
