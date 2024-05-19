package com.casino.blackjack.model.validation.deposit;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CustomCreditCardNumberValidator.class)
public @interface CustomCreditCardNumber {

    String message() default "{constraint.custom.credit.card.number}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
