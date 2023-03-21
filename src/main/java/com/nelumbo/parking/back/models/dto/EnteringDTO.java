package com.nelumbo.parking.back.models.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public record EnteringDTO (@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="America/Bogota") Date date, String parking, String vehicle){

}
