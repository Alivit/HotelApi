package com.alivit.hotelservice.validation.annotation;

import com.alivit.hotelservice.validation.PhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {
    String message() default "This phone number does not exist.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
