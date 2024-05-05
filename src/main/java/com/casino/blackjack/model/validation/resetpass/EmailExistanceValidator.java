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

public class EmailExistanceValidator implements ConstraintValidator<EmailExistance, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailExistanceValidator.class);

    private String email;

    private String message;

    private final UserService userService;

    public EmailExistanceValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(EmailExistance constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        email = constraintAnnotation.emailField();

        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

        String emailValue = (String) beanWrapper.getPropertyValue(this.email);

        Optional<UserEntity> byEmail = userService.findByEmail(emailValue);

        boolean empty = byEmail.isEmpty();

        if (empty) {
            return false;
        }

        return byEmail.get().getIsActive();
    }
}
