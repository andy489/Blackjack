package com.casino.blackjack.model.validation.registration;

import com.casino.blackjack.service.UserService;
import jakarta.validation.ConstraintValidator;

import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserService userService;

    public UniqueUsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userService.findByUsername(value).isEmpty();
    }
}
