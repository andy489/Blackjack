package com.casino.blackjack.model.validation.registration;

import com.casino.blackjack.service.UserService;
import jakarta.validation.ConstraintValidator;

import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userService.findByEmail(value).isEmpty();
    }
}
