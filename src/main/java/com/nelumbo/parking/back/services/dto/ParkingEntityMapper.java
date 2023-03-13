package com.nelumbo.parking.back.services.dto;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelumbo.parking.back.models.dto.ParkingDTO;
import com.nelumbo.parking.back.models.entities.Parking;
import com.nelumbo.parking.back.services.business.IUserService;

@Service
public class ParkingEntityMapper implements Function<ParkingDTO, Parking> {

	@Autowired
	IUserService userService;

	
	
	@Override
	public Parking apply(ParkingDTO t) {
		return new Parking(t.getIdparking(), t.getName(), userService.findByName(t.getPartner()), t.getSpots());
	}

}
