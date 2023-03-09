package com.nelumbo.parking.back.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParkingDTO {
	private Long idparking;
	@NotNull(message = "El nombre es requerido")
	@NotBlank(message = "El nombre es requerido")
	private String name;
	private String partner;
	private int spots;

	public ParkingDTO(Long idparking, String name, String partner, int spots) {
		super();
		this.idparking = idparking;
		this.name = name;
		this.partner = partner;
		this.spots = spots;
	}

}
