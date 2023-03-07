package com.nelumbo.parking.back.servicesDTO;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.nelumbo.parking.back.DTO.ParkingDTO;
import com.nelumbo.parking.back.entities.Parking;
import com.nelumbo.parking.back.entities.User;

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
				t.getSpots()
				);
	}

}
