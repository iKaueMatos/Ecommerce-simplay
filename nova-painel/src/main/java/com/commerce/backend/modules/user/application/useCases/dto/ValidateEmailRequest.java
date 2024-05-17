package com.commerce.backend.user.application.useCases.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ValidateEmailRequest {
    @NotBlank
    String token;
}
