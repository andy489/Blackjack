package com.casino.blackjack.model.dto;

import com.casino.blackjack.model.validation.resetpass.UsernameAndEmailMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@UsernameAndEmailMatch(
        usernameField = "username",
        emailField = "email",
        message = "{constraint.username.email.mismatch}"
)
public class UserResetPasswordDTO {

    @NotBlank(message = "{constraint.not.blank}")
    @Size(min = 3, max = 30, message = "{constraint.username.size}")
    private String username;

    @NotBlank(message = "{constraint.not.blank}")
    @Email
    private String email;
}
