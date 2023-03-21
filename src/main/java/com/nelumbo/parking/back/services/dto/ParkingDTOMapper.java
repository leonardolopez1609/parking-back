package com.nelumbo.parking.back.services.dto;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.nelumbo.parking.back.models.dto.ParkingDTO;
import com.nelumbo.parking.back.models.entities.Parking;
import com.nelumbo.parking.back.models.entities.User;

@Service
public class ParkingDTOMapper implements Function<Parking, ParkingDTO> {

	@Override
	public ParkingDTO apply(Parking t) {
		if(t.getUser()==null) {
		t.setUser(new User());
		}
		return new ParkingDTO(
				t.getIdparking(),
				t.getName(),
				t.getUser().getName(),
				t.getAllSpots(),
				t.getSpotsTaken()
				);
	}

}
