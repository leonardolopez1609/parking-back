package com.nelumbo.parking.back.models.dto;

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
	private int allSpots;
	private int spotsTaken;
	
	
	public ParkingDTO(Long idparking,String name, String partner,
			int allSpots, int spotsTaken) {
		super();
		this.idparking = idparking;
		this.name = name;
		this.partner = partner;
		this.allSpots = allSpots;
		this.spotsTaken = spotsTaken;
	}


	public ParkingDTO(String name, int allSpots) {
		super();
		this.name = name;
		this.allSpots = allSpots;
	}


	public ParkingDTO() {
		super();
	}
	
	

	

}
