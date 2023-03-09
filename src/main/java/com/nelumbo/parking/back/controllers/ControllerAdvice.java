package com.nelumbo.parking.back.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.nelumbo.parking.back.entities.ErrorData;
import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.exceptions.RequestException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;

@RestControllerAdvice
public class ControllerAdvice {

	
	@ExceptionHandler(value = RequestException.class)
	public ResponseEntity<ErrorData> requestExceptionHandler(RequestException ex) {
		ErrorData error = ErrorData.builder().message(ex.getMessage()).build();
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<ErrorData> businessExceptionHandler(BusinessException ex) {
		ErrorData error = ErrorData.builder().message(ex.getMessage()).build();
		return new ResponseEntity<>(error, ex.getStatus());
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorData> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
		ErrorData error = ErrorData.builder().message(ex.getFieldError().getDefaultMessage()).build();
		return new ResponseEntity<>(error,ex.getStatusCode());
	}
	
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<ErrorData> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex) {
		ErrorData error = ErrorData.builder().message(ex.getRootCause().getLocalizedMessage()).build();
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = UnexpectedTypeException.class)
	public ResponseEntity<ErrorData> unexpectedTypeExceptionHandler(UnexpectedTypeException ex) {
		ErrorData error = ErrorData.builder().message(ex.getMessage()).build();
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<ErrorData> constraintViolationExceptionHandler(ConstraintViolationException ex) {
		ErrorData error = ErrorData.builder().message(ex.fillInStackTrace().getLocalizedMessage()).build();
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}

}
