package com.casino.blackjack.model.validation.resetpass;

import com.casino.blackjack.model.entity.UserEntity;
import com.casino.blackjack.service.auth.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.util.Optional;

public class UsernameAndEmailMatchValidator implements ConstraintValidator<UsernameAndEmailMatch, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsernameAndEmailMatchValidator.class);

    private String username;

    private String email;

    private String message;

    private final UserService userService;

    public UsernameAndEmailMatchValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UsernameAndEmailMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        username = constraintAnnotation.usernameField();
        email = constraintAnnotation.emailField();

        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

        String usernameValue = (String) beanWrapper.getPropertyValue(this.username);
        String emailValue = (String) beanWrapper.getPropertyValue(this.email);

        Optional<UserEntity> byUsername = userService.findByUsername(usernameValue);

        boolean empty = byUsername.isEmpty();

        if (empty) {
            return false;
        }

        UserEntity userEntity = byUsername.get();

        Boolean isActive = userEntity.getIsActive();
        String emailFromEntity = userEntity.getEmail();

        if (!isActive) {
            return false;
        }

        return emailFromEntity.equals(emailValue);
    }
}
