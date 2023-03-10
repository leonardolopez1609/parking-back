package com.nelumbo.parking.back.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

	private HttpStatus status;

	public BusinessException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

}
