package com.casino.blackjack.model.view;

import com.casino.blackjack.model.dto.CreditCardBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.casino.blackjack.model.validation.deposit.NotExpiredValidator.checkCurrentMonthBeforeExpiredMonth;

@Getter
@Setter
@Accessors(chain = true)
public class CreditCardsManageView extends CreditCardBase {

    public String getCardNumber() {
        return super.cardNumber;
    }

    public CreditCardsManageView setCardNumber(String cardNumber) {
        super.cardNumber = cardNumber;
        return this;
    }

    public String getCardHolder() {
        return super.cardHolder;
    }

    public CreditCardsManageView setCardHolder(String cardHolder) {
        super.cardHolder = cardHolder;
        return this;
    }

    public @NotNull Integer getExpiredMonth() {
        return super.expiredMonth;
    }

    public CreditCardsManageView setExpiredMonth(Integer expiredMonth) {
        super.expiredMonth = expiredMonth;
        return this;
    }

    public @NotNull Integer getExpiredYear() {
        return super.expiredYear;
    }

    public CreditCardsManageView setExpiredYear(Integer expiredYear) {
        super.expiredYear = expiredYear;
        return this;
    }

    public String getExpiredMonthAndYear() {
        return (getExpiredMonth() <= 9 ? "0" : "") +
                getExpiredMonth() +
                "/" +
                getExpiredYear();
    }

    public Boolean notExpired() {
        return checkCurrentMonthBeforeExpiredMonth(expiredYear, expiredMonth);
    }
}
