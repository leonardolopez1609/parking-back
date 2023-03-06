package com.nelumbo.parking.back.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorData {
	 private String message;
}
