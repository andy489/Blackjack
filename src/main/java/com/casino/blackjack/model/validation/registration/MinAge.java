package com.casino.blackjack.model.validation.registration;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = MinAgeValidator.class)
public @interface MinAge {

    int min() default 18;

    String message() default "{constraint.birth.date.min.age}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
