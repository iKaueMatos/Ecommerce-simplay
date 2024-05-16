package com.commerce.backend.order.application.service;

import com.commerce.backend.cart.application.useCases.dto.CartResponse;
import com.commerce.backend.cart.application.useCases.dto.ConfirmCartRequest;
import com.commerce.backend.cart.infra.entity.Cart;

public interface CartService {
    CartResponse addToCart(Long id, Integer amount);
    CartResponse incrementCartItem(Long cartItemId, Integer amount);
    CartResponse decrementCartItem(Long cartItemId, Integer amount);
    CartResponse fetchCart();
    CartResponse removeFromCart(Long id);
    boolean confirmCart(ConfirmCartRequest confirmCartRequest);
    Cart getCart();
    void saveCart(Cart cart);
    void emptyCart();
    Cart calculatePrice(Cart cart);
}
