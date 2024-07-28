package com.casino.blackjack.model.dto;

import com.casino.blackjack.model.validation.creditcard.FutureExpirationDate;
import com.casino.blackjack.model.validation.creditcard.MaxCreditCardRegisteredLimitReached;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@FutureExpirationDate(
        expiredMonthField = "expiredMonth",
        expiredYearField = "expiredYear",
        message = "{constraint.expired.date}"
)
@MaxCreditCardRegisteredLimitReached(
        max = 3,
        cardNumberField = "cardNumber",
        message = "{constraint.credit.card.limit.reached}"
)
@ToString(callSuper = true)
public class CreditCardDTO extends CreditCardBase {

    @NotNull(message = "{constraint.not.null}")
    @Pattern(regexp = "^\\d{3}$", message = "{constraint.cvc}")
    public String cardCvc;

    public String getCardNumber(){
        return super.cardNumber;
    }

    public CreditCardDTO setCardNumber(String cardNumber){
        super.cardNumber = cardNumber;
        return this;
    }

    public String getCardHolder(){
        return super.cardHolder;
    }

    public CreditCardDTO setCardHolder(String cardHolder){
        super.cardHolder = cardHolder;
        return this;
    }

    public @NotNull Integer getExpiredMonth(){
        return super.expiredMonth;
    }

    public CreditCardDTO setExpiredMonth(Integer expiredMonth){
        super.expiredMonth = expiredMonth;
        return this;
    }

    public @NotNull Integer getExpiredYear(){
        return super.expiredYear;
    }

    public CreditCardDTO setExpiredYear(Integer expiredYear){
        super.expiredYear = expiredYear;
        return this;
    }
}
