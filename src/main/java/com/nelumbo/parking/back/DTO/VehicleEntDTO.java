package com.nelumbo.parking.back.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class VehicleEntDTO {

	private Long idvehicle;
	private String plate;
	private Date enteringDate;

	public VehicleEntDTO(Long idvehicle, String plate, Date enteringDate) {
		super();
		this.idvehicle = idvehicle;
		this.plate = plate;
		this.enteringDate = enteringDate;
	}

}
