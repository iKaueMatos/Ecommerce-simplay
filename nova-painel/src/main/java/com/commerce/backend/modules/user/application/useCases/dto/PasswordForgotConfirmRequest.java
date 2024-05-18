package com.commerce.backend.modules.user.application.useCases.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordForgotConfirmRequest {
    @NotBlank
    String token;
}
