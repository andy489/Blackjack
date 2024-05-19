package com.casino.blackjack.model.dto;

import com.casino.blackjack.model.validation.deposit.CustomCreditCardNumber;
import com.casino.blackjack.model.validation.deposit.FutureExpirationDate;
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
public class PayCardDTO {

    @NotBlank
    @CustomCreditCardNumber(message = "{constraint.custom.credit.card.number}")
    private String cardNumber;

    @NotBlank
    @Size(min = 3, max = 30, message = "{constraint.full-name.size}")
    private String cardHolder;

    @NotNull
    @Min(value = 1L, message =  "{constraint.expired.month}")
    @Max(value = 12L, message = "{constraint.expired.month}")
    private Integer expiredMonth;

    @NotNull
    @Max(value = 3000L, message = "{constraint.expired.year}")
    private Integer expiredYear;

    @NotNull
    @Min(value = 100L, message =  "{constraint.cvv}")
    @Max(value = 999L, message = "{constraint.cvv}")
    private Integer cardCvv;

    public PayCardDTO() {
    }

    @Override
    public String toString() {
        return "PayCardDTO{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardName='" + cardHolder + '\'' +
                ", cardMonth=" + expiredMonth +
                ", cardYear=" + expiredYear +
                ", cardCvv='" + cardCvv + '\'' +
                '}';
    }
}
