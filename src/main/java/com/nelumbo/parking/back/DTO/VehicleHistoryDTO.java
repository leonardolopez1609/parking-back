package com.nelumbo.parking.back.DTO;

import java.util.Date;

public record VehicleHistoryDTO (Long idparking,Long idvehicle, String plate, Date enteringDate, Date departureDate) {

}
