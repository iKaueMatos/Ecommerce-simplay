package com.commerce.backend.cart.application.useCases.dto;

import lombok.Data;

import java.util.List;

import com.commerce.backend.cart.domain.model.CartItemDTO;
import com.commerce.backend.order.domain.model.DiscountDTO;

@Data
public class CartResponse {
    private List<CartItemDTO> cartItems;
    private DiscountDTO discount;
    private Float totalCartPrice;
    private Float totalCargoPrice;
    private Float totalPrice;
}
