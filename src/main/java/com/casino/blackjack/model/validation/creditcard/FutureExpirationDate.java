package com.casino.blackjack.model.validation.creditcard;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = FutureExpirationDateValidator.class)
public @interface FutureExpirationDate {

    String message() default "{constraint.expired.date}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String expiredMonthField();

    String expiredYearField();
}