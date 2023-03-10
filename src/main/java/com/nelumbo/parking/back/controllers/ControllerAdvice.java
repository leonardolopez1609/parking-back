package com.nelumbo.parking.back.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yaml.snakeyaml.serializer.SerializerException;

import com.nelumbo.parking.back.entities.ErrorData;
import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.exceptions.RequestException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;

@RestControllerAdvice
public class ControllerAdvice {

	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handleValidateExceptions(MethodArgumentNotValidException ex) {
		Map<String, Object> errors = new HashMap<String, Object>();
		List<ErrorData> errorsList = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			ErrorData errorData = ErrorData.builder().message(error.getDefaultMessage()).build();
			errorsList.add(errorData);
		});
		
		errors.put("Errors", errorsList);
		return errors;
	}
	
	
	
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
/**
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorData> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
		ErrorData error = ErrorData.builder().message(ex.getFieldError().getDefaultMessage()).build();
		return new ResponseEntity<>(error, ex.getStatusCode());
	}**/

	// -------------Reemplazar por anotacion personalizada
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<ErrorData> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex) {
		String param = ex.getRootCause().fillInStackTrace().getMessage()
				.substring(94)
				.replace(" la llave", "")
				.replace(")=(", ": ")
				.replace("(", "")
				.replace(")", "");
		String errorMessage = param;
		ErrorData error = ErrorData.builder().message(errorMessage).build();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = UnexpectedTypeException.class)
	public ResponseEntity<ErrorData> unexpectedTypeExceptionHandler(UnexpectedTypeException ex) {
		ErrorData error = ErrorData.builder().message(ex.getMessage()).build();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<ErrorData> constraintViolationExceptionHandler(ConstraintViolationException ex) {
		ErrorData error = ErrorData.builder().message(ex.getMessage()).build();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = SerializerException.class)
	public ResponseEntity<ErrorData> accessDeniedException(SerializerException ex) {
		ErrorData error = ErrorData.builder().message(ex.fillInStackTrace().getLocalizedMessage()).build();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	
	//AccessDeniedException
}
