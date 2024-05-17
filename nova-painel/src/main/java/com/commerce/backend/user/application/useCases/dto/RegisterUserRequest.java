package com.commerce.backend.user.application.useCases.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.commerce.backend.shared.validator.CustomEmail;
import com.commerce.backend.shared.validator.PasswordMatches;

@Data
@PasswordMatches
public class RegisterUserRequest {

    @NotBlank
    @Size(min = 3, max = 52)
    @CustomEmail
    private String email;

    @NotBlank
    @Size(min = 6, max = 52)
    private String password;

    @NotBlank
    @Size(min = 6, max = 52)
    private String passwordRepeat;
}
