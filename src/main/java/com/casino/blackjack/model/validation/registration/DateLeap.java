package com.casino.blackjack.model.validation.registration;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = DateLeapValidator.class)
public @interface DateLeap {

    String message() default "{constraint.birth-date.invalid-date}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
