package com.commerce.backend.cart.application.converter;

import com.commerce.backend.cart.application.useCases.dto.CartResponse;
import com.commerce.backend.cart.domain.model.CartItemDTO;
import com.commerce.backend.cart.infra.entity.Cart;
import com.commerce.backend.order.domain.model.DiscountDTO;
import com.commerce.backend.product.domain.model.ColorDTO;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CartResponseConverter implements Function<Cart, CartResponse> {

        @Override
        public CartResponse apply(Cart cart) {
                CartResponse cartResponse = new CartResponse();

                List<CartItemDTO> cartItems = cart.getCartItemList()
                                .stream()
                                .map(cartItem -> CartItemDTO.builder()
                                                .id(cartItem.getId())
                                                .url(cartItem.getProductVariant().getProduct().getUrl())
                                                .name(cartItem.getProductVariant().getProduct().getName())
                                                .price(cartItem.getProductVariant().getPrice())
                                                .amount(cartItem.getAmount())
                                                .thumb(cartItem.getProductVariant().getThumb())
                                                .stock(cartItem.getProductVariant().getStock())
                                                .color(ColorDTO.builder()
                                                                .name(cartItem.getProductVariant().getColor().getName())
                                                                .hex(cartItem.getProductVariant().getColor().getHex())
                                                                .build())
                                                .build())
                                .collect(Collectors.toList());

                cartResponse.setCartItems(cartItems);

                if (Objects.nonNull(cart.getDiscount())) {
                        cartResponse.setDiscount(DiscountDTO.builder()
                                        .discountPercent(cart.getDiscount().getDiscountPercent())
                                        .status(cart.getDiscount().getStatus())
                                        .build());
                }

                cartResponse.setTotalCartPrice(cart.getTotalCartPrice());
                cartResponse.setTotalCargoPrice(cart.getTotalCargoPrice());
                cartResponse.setTotalPrice(cart.getTotalPrice());
                return cartResponse;
        }
}
