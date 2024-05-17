package com.commerce.backend.order.domain.service;

import com.commerce.backend.cart.infra.entity.Cart;
import com.commerce.backend.core.error.exception.InvalidArgumentException;
import com.commerce.backend.core.error.exception.ResourceNotFoundException;
import com.commerce.backend.order.application.service.CartService;
import com.commerce.backend.order.application.service.DiscountService;
import com.commerce.backend.order.infra.entity.Discount;
import com.commerce.backend.order.infra.repository.DiscountRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final CartService cartService;

    @Autowired
    public DiscountServiceImpl(DiscountRepository discountRepository, CartService cartService) {
        this.discountRepository = discountRepository;
        this.cartService = cartService;
    }

    @Override
    public void applyDiscount(String code) {
        Discount discount = discountRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Discount code not found"));

        if (discount.getStatus() != 1) {
            throw new InvalidArgumentException("Discount code is expired!");
        }

        Cart cart = cartService.getCart();

        cart.setDiscount(discount);
        cart = cartService.calculatePrice(cart);
        cartService.saveCart(cart);
    }
}
