package com.nelumbo.parking.back.DTO;

import com.nelumbo.parking.back.customvalidators.ValueParkingName;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ParkingDTO {
	private Long idparking;
	
	@NotBlank(message = "El nombre es requerido")
	@ValueParkingName
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
