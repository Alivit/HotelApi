package com.alivit.hotelservice.validation;

import com.alivit.hotelservice.validation.annotation.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private static final String PHONE_PATTERN = "^\\+[1-9]\\d{1,3}(?:[\\s\\-]?\\d){8,20}$";

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneNumber.isBlank()) {
            return false;
        }

        return Pattern.matches(PHONE_PATTERN, phoneNumber);
    }
}
