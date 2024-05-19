package com.casino.blackjack.model.validation.deposit;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomCreditCardNumberValidator implements ConstraintValidator<CustomCreditCardNumber, String> {

    @Override
    public boolean isValid(String cardNumberRaw, ConstraintValidatorContext context) {

        String onlyDigits = cardNumberRaw.replaceAll("[^0-9]", "");

        return onlyDigits.length() == 16;
    }
}
