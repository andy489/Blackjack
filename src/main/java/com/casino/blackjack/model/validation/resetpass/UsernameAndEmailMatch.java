package com.casino.blackjack.model.validation.resetpass;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UsernameAndEmailMatchValidator.class)
public @interface UsernameAndEmailMatch {

    String usernameField();

    String message() default "{constraint.fields.mismatch}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String emailField();
}

