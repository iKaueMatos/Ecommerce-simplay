package com.commerce.backend.modules.cart.application.useCases.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AddToCartRequest {

    @NotNull
    @Min(1)
    private Long productVariantId;

    @NotNull
    @Min(1)
    private Integer amount;
}
