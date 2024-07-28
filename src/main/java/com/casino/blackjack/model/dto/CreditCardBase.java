package com.casino.blackjack.model.dto;

import com.casino.blackjack.model.validation.creditcard.CustomCreditCardNumber;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public abstract class CreditCardBase {

    @NotBlank(message = "{constraint.not.blank}")
    @CustomCreditCardNumber(message = "{constraint.custom.credit.card.number}")
    protected String cardNumber;

    @NotBlank(message = "{constraint.not.blank}")
    @Size(min = 3, max = 30, message = "{constraint.full-name.size}")
    protected String cardHolder;

    @NotNull(message = "{constraint.not.null}")
    @Min(value = 1L, message = "{constraint.expired.month}")
    @Max(value = 12L, message = "{constraint.expired.month}")
    protected Integer expiredMonth;

    @NotNull(message = "{constraint.not.null}")
    @Max(value = 3000L, message = "{constraint.expired.year}")
    protected Integer expiredYear;

    public String getCardNumberPartlyHidden() {
        final int FIRST_REVEALED_DIGITS = 4;
        final int LAST_REVEALED_DIGITS = 4;

        StringBuilder sb = new StringBuilder();

        if (cardNumber.length() > FIRST_REVEALED_DIGITS) {
            sb.append(cardNumber, 0, FIRST_REVEALED_DIGITS);
        }

        for (int i = FIRST_REVEALED_DIGITS; i < cardNumber.length() - LAST_REVEALED_DIGITS; i++) {
            if (Character.isDigit(cardNumber.charAt(i))) {
                sb.append('*');
            } else {
                sb.append(' ');
            }
        }

        if (cardNumber.length() > LAST_REVEALED_DIGITS) {
            sb.append(cardNumber, cardNumber.length() - LAST_REVEALED_DIGITS, cardNumber.length());
        }

        return sb.toString();
    }

    public String getValidThru() {

        return (expiredMonth < 10 ? "0" + expiredMonth : expiredMonth) +
                "/" +
                expiredYear;
    }

    public String getTypeDisplay() {
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

    public String getTypeLogo() {
        if (cardNumber.startsWith("4")) {
            return "visa";
        }

        if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
            return "amex";
        }

        char sec = cardNumber.charAt(1);

        if (cardNumber.startsWith("5") && (sec >= 1 && sec <= '5')) {
            return "mastercard";
        }

        if (cardNumber.startsWith("6011")) {
            return "discover";
        }

        if (cardNumber.startsWith("9792")) {
            return "troy";
        }

        return "visa";
    }
}
