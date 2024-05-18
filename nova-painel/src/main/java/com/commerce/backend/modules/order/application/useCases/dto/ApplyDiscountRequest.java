package com.commerce.backend.modules.order.application.useCases.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ApplyDiscountRequest {

    @NotBlank
    private String code;
}
