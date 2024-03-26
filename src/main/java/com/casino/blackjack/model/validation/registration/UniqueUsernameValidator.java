package com.casino.blackjack.model.validation.registration;

import com.casino.blackjack.model.entity.UserEntity;
import com.casino.blackjack.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintValidator;

import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniqueUsernameValidator.class);

    private final UserService userService;

    public UniqueUsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public boolean isValid(String username, ConstraintValidatorContext context) {

        Optional<UserEntity> byUsername = userService.findByUsername(username);

        boolean empty = byUsername.isEmpty();

        if (empty) {
            return true;
        }

        UserEntity userEntity = byUsername.get();

        if (!userEntity.getIsActive()) {
            LOGGER.info("Non-active user with username {} was deleted", username);

            userService.deleteActivationTokenByUserId(userEntity.getId());
            userService.deleteByUsername(username);

            return true;
        }

        return false;
    }
}
