package com.alivit.hotelservice.validation;

import com.alivit.hotelservice.validation.annotation.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private static final String PHONE_PATTERN = "^[+]{1}(?:[0-9\\-\\(\\)\\/\\.]\\s?){6,15}[0-9]{1}$";

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneNumber.isBlank()) {
            return false;
        }

        return Pattern.matches(PHONE_PATTERN, phoneNumber);
    }
}
