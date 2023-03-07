package com.nelumbo.parking.back.DTO;

import java.util.List;
import com.nelumbo.parking.back.entities.Vehicle;

import lombok.Data;

@Data
public class ParkingVehicleDTO {
	private Long idparking;
	private String name;
	private String user;
	private int spots;
	private List<VehicleEntDTO> vehicles;

	public ParkingVehicleDTO(Long idparking, String name, String user, int spots, List<VehicleEntDTO> vehicles) {
		super();
		this.idparking = idparking;
		this.name = name;
		this.user = user;
		this.spots = spots;
		this.vehicles = vehicles;
	}

}
