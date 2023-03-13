package com.nelumbo.parking.back.customvalidators;

import org.springframework.beans.factory.annotation.Autowired;

import com.nelumbo.parking.back.services.business.IParkingService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValueParkingNameValidator implements ConstraintValidator<ValueParkingName, String>{

	@Autowired
	private IParkingService parkingService;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		try {
			parkingService.findOneByName(value);
		} catch (Exception e) {
			return true;
		}
		
		return false;
	}

}
