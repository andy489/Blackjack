package com.casino.blackjack.model.validation.deposit;

import com.casino.blackjack.model.validation.deposit.NotExpiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotExpiredValidator.class)
public @interface NotExpired {

    String message() default "{constraint.dep.expired.default}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
