package com.nelumbo.parking.back.models.dto;

import java.util.Date;

public record HistoryDTO (Date enteringDate, Date departureDate, String parking, String vehicle){

}
