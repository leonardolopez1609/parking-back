package com.nelumbo.parking.back.DTO;

import java.util.Date;

public record HistoryDTO (Date enteringDate, Date departureDate, String parking, String vehicle){

}
