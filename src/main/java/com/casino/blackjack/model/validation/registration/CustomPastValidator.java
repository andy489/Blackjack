package com.casino.blackjack.model.validation.registration;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class CustomPastValidator implements ConstraintValidator<CustomPast, String> {

    @Override
    public boolean isValid(String rawData, ConstraintValidatorContext context) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        try {
            Date date = formatter.parse(rawData);

            LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime now = LocalDateTime.now();

            return now.isAfter(localDateTime);

        } catch (ParseException e) {
            return true; // @Pattern will be invalid
        }
    }
}