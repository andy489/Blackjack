package com.casino.blackjack.model.validation.creditcard;

import com.casino.blackjack.model.dto.CreditCardDTO;
import com.casino.blackjack.service.CreditCardService;
import com.casino.blackjack.service.auth.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class MaxCreditCardRegisteredLimitReachedValidator implements
        ConstraintValidator<MaxCreditCardRegisteredLimitReached, Object> {

    private final UserService userService;

    private final CreditCardService creditCardService;

    private Integer max;

    private String cardNumberField;

    private String message;

    public MaxCreditCardRegisteredLimitReachedValidator(UserService userService, CreditCardService creditCardService) {
        this.userService = userService;
        this.creditCardService = creditCardService;
    }

    @Override
    public void initialize(MaxCreditCardRegisteredLimitReached constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        this.max = constraintAnnotation.max();
        this.cardNumberField = constraintAnnotation.cardNumberField();
        this.message = constraintAnnotation.message();
    }

    @Override
    @Transactional
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Long currentLoggedUserId = userService.getCurrentLoggedUserId();

        List<CreditCardDTO> registeredCreditCards = creditCardService.getRegisteredCreditCards(currentLoggedUserId);

        boolean valid = registeredCreditCards.size() < max;

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(cardNumberField)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
