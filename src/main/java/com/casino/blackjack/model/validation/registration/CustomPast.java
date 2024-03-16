package com.casino.blackjack.model.validation.registration;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CustomPastValidator.class)
public @interface CustomPast {

    String message() default "{constraint.past.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
