package com.casino.blackjack.model.dto;

import com.casino.blackjack.model.validation.deposit.CreditCard;
import com.casino.blackjack.model.validation.creditcard.CustomCreditCardNumber;
import com.casino.blackjack.model.validation.deposit.NotExpired;
import com.casino.blackjack.model.validation.deposit.CVC;
import com.casino.blackjack.model.validation.deposit.Currency;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@CreditCard(
        cardNumberField = "cardNumber",
        cvcDepositField = "cvcDeposit",
        message = "{constraint.dep.card-num.cvc.match}"
)
public class DepositDTO {

    @NotBlank(message = "{constraint.not.blank}")
    @CVC(message = "{constraint.dep.invalid.cvc.format}")
    private String cvcDeposit;

    @NotBlank(message = "{constraint.not.blank}")
    @CustomCreditCardNumber
    @NotExpired
    private String cardNumber;

    @NotBlank(message = "{constraint.not.blank}")
    @Currency(message = "{constraint.dep.sum}")
    private String depositSum;
}
