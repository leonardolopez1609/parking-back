package com.nelumbo.parking.back.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailContentDTO {
	@NotNull(message = "El email es requerido")
	@NotBlank(message = "El email es requerido")
	String email;
	@NotNull(message = "La placa es requerida")
	@NotBlank(message = "La placa es requerida")
	String plate;
	@NotNull(message = "El mensaje es requerido")
	@NotBlank(message = "El mensaje es requerido")
	String message;
	@NotNull(message = "El nombre del parqueadero es requerido")
	@NotBlank(message = "El nombre del parqueadero es requerido")
	String parking;
}
