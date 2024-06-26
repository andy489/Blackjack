package com.casino.blackjack.model.validation.deposit;

import com.casino.blackjack.model.dto.CreditCardDTO;
import com.casino.blackjack.service.CreditCardService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;
import java.util.Optional;

public class NotExpiredValidator implements ConstraintValidator<NotExpired, String> {

    private final CreditCardService creditCardService;

    public NotExpiredValidator(CreditCardService creditCardService) {

        this.creditCardService = creditCardService;
    }

    @Override
    public boolean isValid(String cardNum, ConstraintValidatorContext context) {

        Optional<CreditCardDTO> byCardNumber = creditCardService.getByCardNumber(cardNum);

        if (byCardNumber.isEmpty()) {
            return false;
        }

        CreditCardDTO creditCardDTO = byCardNumber.get();

        Integer expiredYear = creditCardDTO.getExpiredYear();
        Integer expiredMonth = creditCardDTO.getExpiredMonth();

        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH);

        if (currentYear < expiredYear) {
            return true;
        }

        return currentMonth < expiredMonth;
    }
}
