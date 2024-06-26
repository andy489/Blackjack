package com.casino.blackjack.model.validation.deposit;

import com.casino.blackjack.model.validation.deposit.CreditCard;
import com.casino.blackjack.service.CreditCardService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class CreditCardValidator implements ConstraintValidator<CreditCard, Object> {

    private final CreditCardService creditCardService;

    private String cardNumberField;

    private String cvcDepositField;

    private String message;

    public CreditCardValidator(CreditCardService creditCardService) {

        this.creditCardService = creditCardService;
    }

    @Override
    public void initialize(CreditCard constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        this.cardNumberField = constraintAnnotation.cardNumberField();
        this.cvcDepositField = constraintAnnotation.cvcDepositField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

        Object cardNumberFieldValue = beanWrapper.getPropertyValue(this.cardNumberField);
        Object cvcDepositFieldValue = beanWrapper.getPropertyValue(this.cvcDepositField);

        if (cardNumberFieldValue == null || cvcDepositFieldValue == null) {
            return false;
        }

        String cardNum = cardNumberFieldValue.toString();
        String cvc =  cvcDepositFieldValue.toString();

        Boolean valid = creditCardService.checkCreditCardNumberAndCvcMatch(cardNum, cvc);

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(cvcDepositField)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
