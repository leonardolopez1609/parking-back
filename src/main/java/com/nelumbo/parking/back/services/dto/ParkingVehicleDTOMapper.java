package com.nelumbo.parking.back.services.dto;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelumbo.parking.back.models.dto.ParkingVehicleDTO;
import com.nelumbo.parking.back.models.entities.Parking;
import com.nelumbo.parking.back.services.business.IParkingService;

@Service
public class ParkingVehicleDTOMapper implements Function<Parking, ParkingVehicleDTO> {

	@Autowired
	private IParkingService parkingService;

	@Override
	public ParkingVehicleDTO apply(Parking t) {

		return new ParkingVehicleDTO(t.getIdparking(), t.getName(), t.getUser().getName(), t.getSpots(),
				parkingService.vehiclesByParkingInd(t.getIdparking()));
	}

}
