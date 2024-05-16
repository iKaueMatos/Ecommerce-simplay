package com.commerce.backend.cart.application.useCases.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RemoveFromCartRequest {

    @NotNull
    @Min(1)
    private Long cartItemId;
}