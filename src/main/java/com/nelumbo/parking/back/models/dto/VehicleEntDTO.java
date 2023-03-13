package com.nelumbo.parking.back.models.dto;

import java.util.Date;

import lombok.Data;

@Data
public class VehicleEntDTO {

	private Long idparking;
	private Long idvehicle;
	private String plate;
	private Date enteringDate;

	public VehicleEntDTO(Long idparking,Long idvehicle, String plate, Date enteringDate) {
		super();
		this.idvehicle = idvehicle;
		this.plate = plate;
		this.enteringDate = enteringDate;
		this.idparking=idparking;
	}

}
