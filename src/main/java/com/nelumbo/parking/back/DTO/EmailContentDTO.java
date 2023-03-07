package com.nelumbo.parking.back.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailContentDTO {
	String email;
	String plate;
	String message;
	String parking;
}
