package com.casino.blackjack.model.dto;


import com.casino.blackjack.model.validation.registration.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@FieldMatch(
        firstField = "password",
        secondField = "confirmPassword",
        message = "{constraint.password.mismatch}"
)
public class ResetPasswordDTO {

    @NotBlank(message = "{constraint.not.blank}")
    @Size(min = 4, max = 20, message = "{constraint.password.size}")
    private String password;

    @NotBlank(message = "{constraint.not.blank}")
    @Size(min = 4, max = 20, message = "{constraint.password.size}")
    private String confirmPassword;
}
