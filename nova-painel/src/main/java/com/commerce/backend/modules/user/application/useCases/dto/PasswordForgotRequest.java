package com.commerce.backend.modules.user.application.useCases.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.commerce.backend.shared.validator.CustomEmail;

@Data
public class PasswordForgotRequest {

    @NotBlank
    @CustomEmail
    @Size(min = 3, max = 52)
    private String email;
}
