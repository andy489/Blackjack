package com.casino.blackjack.model.dto;


import com.casino.blackjack.model.enumerated.GenderEnum;
import com.casino.blackjack.model.validation.registration.DateLeap;
import com.casino.blackjack.model.validation.registration.CustomPast;
import com.casino.blackjack.model.validation.registration.FieldMatch;
import com.casino.blackjack.model.validation.registration.MinAge;
import com.casino.blackjack.model.validation.registration.UniqueEmail;
import com.casino.blackjack.model.validation.registration.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class UserRegistrationDTO {

    // Date time format MM/dd/yyyy rough check
    private static final String DATE_PATTERN = "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((?:19|20)[0-9][0-9])$";

    @NotBlank(message = "{constraint.not.blank}")
    @Size(min = 3, max = 30, message = "{constraint.username.size}")
    @UniqueUsername
    private String username;

    @NotBlank(message = "{constraint.not.blank}")
    @Size(min = 2, max = 20, message = "{constraint.first.name.size}")
    private String firstName;

    @NotBlank(message = "{constraint.not.blank}")
    @Size(min = 2, max = 20, message = "{constraint.last.name.size}")
    private String lastName;

    @NotNull(message = "{constraint.not.null}")
    // The following regex will accept dd/MM/yyyy
    @Pattern(regexp = DATE_PATTERN, message = "{constraint.birth.date.pattern}")
    @DateLeap
    @CustomPast
    @MinAge(min = 18)
    private String birthDate;

    @NotBlank(message = "{constraint.not.blank}")
    @Email
    @UniqueEmail
    private String email;

    @NotNull(message = "{constraint.not.null}")
    private GenderEnum gender;

    @NotBlank(message = "{constraint.not.blank}")
    @Size(min = 4, max = 20, message = "{constraint.password.size}")
    private String password;

    private String confirmPassword;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "UserRegisterDto{" +
                "userName='" + username + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + (password != null ? "[PROVIDED]" : null) + '\'' +
                ", confirmPassword='" + (confirmPassword != null ? "[PROVIDED]" : null) + '\'' +
                '}';
    }
}
