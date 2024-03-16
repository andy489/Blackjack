package com.casino.blackjack.model.validation.registration;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateLeapValidator implements ConstraintValidator<DateLeap, String> {

    @Override
    public boolean isValid(String rawData, ConstraintValidatorContext context) {

        boolean isValid = true;

        String[] s = rawData.split("/");

        if (s.length != 3) {
            return true; // @Pattern will be invalid
        }

        String day = s[0];
        String month = s[1];
        String year = s[2];

        int yyyy;

        try {
            yyyy = Integer.parseInt(year);
        } catch (RuntimeException e) {
            return true; // @Pattern will be invalid
        }

        if ((month.equals("4") || month.equals("6") || month.equals("9") ||
                month.equals("04") || month.equals("06") || month.equals("09") ||
                month.equals("11")) && day.equals("31")) {
            isValid = false;
        }

        if (month.equals("2") || month.equals("02")) {
            if (day.equals("30") || day.equals("31")) {
                isValid = false;
            } else if (day.equals("29")) {  // feb 29 days? leap year checking
                if (!isLeapYear(yyyy)) {
                    isValid = false;
                }
            }
        }

        return isValid;
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }

}
