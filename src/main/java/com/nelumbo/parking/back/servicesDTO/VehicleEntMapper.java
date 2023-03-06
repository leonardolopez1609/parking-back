package com.nelumbo.parking.back.servicesDTO;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.nelumbo.parking.back.DTO.VehicleEntDTO;
import com.nelumbo.parking.back.entities.Entering;

@Service
public class VehicleEntMapper implements Function<Entering, VehicleEntDTO> {

	@Override
	public VehicleEntDTO apply(Entering t) {
		return new VehicleEntDTO(t.getVehicle().getIdvehicle(), t.getVehicle().getPlate(), t.getDate());
	}

}
