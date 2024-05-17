package com.commerce.backend.cart.application.useCases.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class DecrementCartItemRequest {
    @NotNull
    @Min(1)
    private Long cartItemId;

    @NotNull
    @Min(1)
    private Integer amount;
}
