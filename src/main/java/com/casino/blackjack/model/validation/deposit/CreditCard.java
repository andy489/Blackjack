package com.casino.blackjack.model.validation.deposit;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = CreditCardValidator.class)
public @interface CreditCard {

    String message() default "{constraint.dep.card-num.cvc.match.default}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String cardNumberField();

    String cvcDepositField();
}
