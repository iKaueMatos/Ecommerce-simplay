package com.commerce.backend.modules.cart.application.useCases.dto;

import lombok.Data;

import java.util.List;

import com.commerce.backend.modules.cart.domain.model.CartItemDTO;
import com.commerce.backend.modules.order.domain.model.DiscountDTO;

@Data
public class CartResponse {
    private List<CartItemDTO> cartItems;
    private DiscountDTO discount;
    private Float totalCartPrice;
    private Float totalCargoPrice;
    private Float totalPrice;
}
