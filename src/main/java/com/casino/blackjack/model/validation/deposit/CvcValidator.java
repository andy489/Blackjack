package com.casino.blackjack.model.validation.deposit;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CvcValidator implements ConstraintValidator<CVC, String> {

    private static final String CVV_PATTERN = "^(\\d{3})$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(CVV_PATTERN);
    }

}
