package com.nelumbo.parking.back.services.dto;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.nelumbo.parking.back.models.dto.VehicleEntDTO;
import com.nelumbo.parking.back.models.entities.Entering;

@Service
public class VehicleEntMapper implements Function<Entering, VehicleEntDTO> {

	
	@Override
	public VehicleEntDTO apply(Entering t) {
		return new VehicleEntDTO(t.getParking().getIdparking(),t.getVehicle().getIdvehicle(), t.getVehicle().getPlate(), t.getDate());
	}

}
