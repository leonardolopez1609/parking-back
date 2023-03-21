package com.nelumbo.parking.back.models.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public record VehicleHistoryDTO (Long idparking,Long idvehicle, String plate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="America/Bogota") Date enteringDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="America/Bogota")Date departureDate) {

}
