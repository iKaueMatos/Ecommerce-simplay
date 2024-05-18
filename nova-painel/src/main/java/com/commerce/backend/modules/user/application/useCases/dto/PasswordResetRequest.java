package com.commerce.backend.modules.user.application.useCases.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.commerce.backend.shared.validator.PasswordMatches;

@Data
@PasswordMatches
public class PasswordResetRequest {

    @NotBlank
    @Size(min = 6, max = 52)
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 52)
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 52)
    private String newPasswordConfirm;
}
