package com.casino.blackjack.model.dto;

import com.casino.blackjack.model.validation.creditcard.CustomCreditCardNumber;
import com.casino.blackjack.model.validation.creditcard.FutureExpirationDate;
import com.casino.blackjack.model.validation.creditcard.MaxCreditCardRegisteredLimitReached;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
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
        message="{constraint.credit.card.limit.reached}"
)
public class CreditCardDTO {

    private static final int FIRST_REVEALED_DIGITS = 4;

    @NotBlank(message = "{constraint.not.blank}")
    @CustomCreditCardNumber(message = "{constraint.custom.credit.card.number}")
    private String cardNumber;

    @NotBlank(message = "{constraint.not.blank}")
    @Size(min = 3, max = 30, message = "{constraint.full-name.size}")
    private String cardHolder;

    @NotNull(message = "{constraint.not.null}")
    @Min(value = 1L, message = "{constraint.expired.month}")
    @Max(value = 12L, message = "{constraint.expired.month}")
    private Integer expiredMonth;

    @NotNull(message = "{constraint.not.null}")
    @Max(value = 3000L, message = "{constraint.expired.year}")
    private Integer expiredYear;

    @NotNull(message = "{constraint.not.null}")
    @Min(value = 100L, message = "{constraint.cvv}")
    @Max(value = 999L, message = "{constraint.cvv}")
    private Integer cardCvc;

    public String getType() {
        if (cardNumber.startsWith("4")) {
            return "VISA";
        }

        if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
            return "American Express";
        }

        char sec = cardNumber.charAt(1);

        if (cardNumber.startsWith("5") && (sec >= 1 && sec <= '5')) {
            return "Mastercard";
        }

        if (cardNumber.startsWith("6011")) {
            return "Discover";
        }

        if (cardNumber.startsWith("9792")) {
            return "Troy";
        }

        return "VISA";
    }

    public String getCardNumberPartlyHidden() {
        StringBuilder sb = new StringBuilder();

        if (cardNumber.length() > FIRST_REVEALED_DIGITS) {
            sb.append(cardNumber, 0, FIRST_REVEALED_DIGITS);
        }

        for (int i = FIRST_REVEALED_DIGITS; i < cardNumber.length(); i++) {
            if (Character.isDigit(cardNumber.charAt(i))) {
                sb.append('*');
            } else {
                sb.append(' ');
            }
        }

        return sb.toString();
    }

    public String getValidThru() {

        return (expiredMonth < 10 ? "0" + expiredMonth : expiredMonth) +
                "/" +
                expiredYear;
    }

    public CreditCardDTO() {
    }

    @Override
    public String toString() {
        return "CreditCardDTO{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardName='" + cardHolder + '\'' +
                ", cardMonth=" + expiredMonth +
                ", cardYear=" + expiredYear +
                ", cardCvv='" + cardCvc + '\'' +
                '}';
    }
}
