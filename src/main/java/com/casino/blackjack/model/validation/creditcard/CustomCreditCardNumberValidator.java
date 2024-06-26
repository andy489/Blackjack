package com.casino.blackjack.model.validation.creditcard;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomCreditCardNumberValidator implements ConstraintValidator<CustomCreditCardNumber, String> {

    private static final int AMERICAN_EXPRESS_LENGTH = 15;
    private static final int VISA_LENGTH = 16;

    @Override
    public boolean isValid(String cardNumberRaw, ConstraintValidatorContext context) {

        String onlyDigits = cardNumberRaw.replaceAll("[^0-9]", "");

        boolean validAmericanExpress = (onlyDigits.startsWith("37") || onlyDigits.startsWith("34"))
                && onlyDigits.length() == AMERICAN_EXPRESS_LENGTH;

        boolean validVisa = onlyDigits.length() == VISA_LENGTH;

        return validVisa || validAmericanExpress;
    }
}
