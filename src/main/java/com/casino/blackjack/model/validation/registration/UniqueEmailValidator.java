package com.casino.blackjack.model.validation.registration;

import com.casino.blackjack.model.entity.UserEntity;
import com.casino.blackjack.service.auth.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintValidator;

import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniqueEmailValidator.class);
    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public boolean isValid(String email, ConstraintValidatorContext context) {
        Optional<UserEntity> byEmail = userService.findByEmail(email);

        boolean empty = byEmail.isEmpty();

        if (empty) {
            return true;
        }

        UserEntity userEntity = byEmail.get();

        if (!userEntity.getIsActive()) {
            LOGGER.info("Non-active user with email {} was deleted", email);

            userService.deleteActivationTokenByUserId(userEntity.getId());
            userService.deleteByEmail(email);

            return true;
        }

        return false;
    }
}
