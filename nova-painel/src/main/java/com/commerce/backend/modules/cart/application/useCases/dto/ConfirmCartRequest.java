package com.commerce.backend.modules.cart.application.useCases.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.commerce.backend.modules.cart.domain.model.CartItemDTO;
import com.commerce.backend.modules.order.domain.model.DiscountDTO;

import java.util.List;

@Data
public class ConfirmCartRequest {

    @NotNull
    private List<CartItemDTO> cartItems;

    private DiscountDTO discount;

    @Min(0)
    private Float totalCartPrice;

    @Min(0)
    private Float totalCargoPrice;

    @Min(0)
    private Float totalPrice;


}
