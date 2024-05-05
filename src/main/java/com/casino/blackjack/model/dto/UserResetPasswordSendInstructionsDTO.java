package com.casino.blackjack.model.dto;

import com.casino.blackjack.model.validation.resetpass.EmailExistance;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@EmailExistance(
        emailField = "email",
        message = "{constraint.username.email.mismatch}"
)
public class UserResetPasswordSendInstructionsDTO {

    @NotBlank(message = "{constraint.not.blank}")
    @Email
    private String email;
}
