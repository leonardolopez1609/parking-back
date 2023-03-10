package com.nelumbo.parking.back.customvalidators;

import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;



import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValuePlateValidator implements ConstraintValidator<ValuePlate, String>{
	
	
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || !value.matches("^[A-Z0-9]*$")||value.length()!=6||value.equals("")) {
			return false;
		}
		
		
		return true;
	}

}
