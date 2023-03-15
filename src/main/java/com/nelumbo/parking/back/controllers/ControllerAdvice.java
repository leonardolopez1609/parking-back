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

import com.nelumbo.parking.back.exceptions.BusinessException;
import com.nelumbo.parking.back.exceptions.RequestException;
import com.nelumbo.parking.back.models.entities.ErrorData;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;

@RestControllerAdvice
public class ControllerAdvice {

	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,List<ErrorData>> handleValidateExceptions(MethodArgumentNotValidException ex) {
		Map<String, List<ErrorData>> errors = new HashMap<String, List<ErrorData>>();
		List<ErrorData> errorsList = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			ErrorData errorData = ErrorData.builder().message(error.getDefaultMessage()).build();
			errorsList.add(errorData);
		});
		
		errors.put("Errors", errorsList);
		return errors;
	}
	
	
	 
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorData> ExceptionHandler(Exception ex) {
		ErrorData error = ErrorData.builder().message(ex.getMessage()).build();
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(value = ServletException.class)
	public ResponseEntity<ErrorData> servletExceptionHandler(ServletException ex) {
		ErrorData error = ErrorData.builder().message(ex.getMessage()).build();
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	
	
	
	@ExceptionHandler(value = ExpiredJwtException.class)
	public ResponseEntity<ErrorData> requestExceptionHandler(ExpiredJwtException ex) {
		ErrorData error = ErrorData.builder().message(ex.getMessage()).build();
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
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
		String m=ex.getMessage();
		
		
		int n1=m.indexOf("messageTemplate='")+17;
		int n2 =m.indexOf("'}");
		
		m = m.substring(n1,n2);
		
		ErrorData error = ErrorData.builder().message(m).build();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	

	@ExceptionHandler(value = SerializerException.class)
	public ResponseEntity<ErrorData> accessDeniedException(SerializerException ex) {
		ErrorData error = ErrorData.builder().message(ex.fillInStackTrace().getLocalizedMessage()).build();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
}
