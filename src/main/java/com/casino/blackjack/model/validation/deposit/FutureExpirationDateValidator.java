package com.casino.blackjack.model.validation.deposit;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.time.LocalDateTime;
import java.time.Month;

public class FutureExpirationDateValidator implements ConstraintValidator<FutureExpirationDate, Object> {

    private String expiredMonthField;

    private String expiredYearField;

    private String message;

    @Override
    public void initialize(FutureExpirationDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        this.expiredMonthField = constraintAnnotation.expiredMonthField();
        this.expiredYearField = constraintAnnotation.expiredYearField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

        Object expiredMonthFieldValue = beanWrapper.getPropertyValue(this.expiredMonthField);
        Object expiredYearFieldValue = beanWrapper.getPropertyValue(this.expiredYearField);

        if (expiredMonthFieldValue == null || expiredYearFieldValue == null) {
            return false;
        }

        int month = Integer.parseInt(expiredMonthFieldValue.toString());
        int year = Integer.parseInt(expiredYearFieldValue.toString());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime of = LocalDateTime.of(year, Month.of(month), 1, 0, 0, 0);

        return now.isBefore(of);
    }
}
