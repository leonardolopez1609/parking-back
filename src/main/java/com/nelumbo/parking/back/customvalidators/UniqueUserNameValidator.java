package com.nelumbo.parking.back.customvalidators;

import org.springframework.beans.factory.annotation.Autowired;
import com.nelumbo.parking.back.services.business.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {
	@Autowired
	private IUserService userService;
	
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		return !userService.findByName(value).isEnabled();
	}

}
