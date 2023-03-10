package com.nelumbo.parking.back.customvalidators;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.nelumbo.parking.back.customvalidators.ValuePlateValidator;
 


@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ValuePlateValidator.class)
@Documented
public @interface ValuePlate {

	String message() default "{com.autentia.core.persistentce.constraints.nif}";
	 
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
}
