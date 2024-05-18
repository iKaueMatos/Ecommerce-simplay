package com.commerce.backend.modules.user.application.useCases.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.commerce.backend.shared.validator.PasswordMatches;

@Data
@PasswordMatches
public class PasswordForgotValidateRequest {

    @NotBlank
    private String token;

    @NotBlank
    @Size(min = 6, max = 52)
    private String newPassword;

    @NotBlank
    private String newPasswordConfirm;
}
