package com.casino.blackjack.model.validation.creditcard;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = MaxCreditCardRegisteredLimitReachedValidator.class)
public @interface MaxCreditCardRegisteredLimitReached {

    String message() default "{constraint.credit.card.limit.reached.default}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int max() default 1;

    String cardNumberField();
}
