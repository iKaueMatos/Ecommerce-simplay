package com.commerce.backend.modules.order.application.useCases.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.commerce.backend.modules.cart.application.useCases.dto.AddToCartRequest;
import com.commerce.backend.modules.cart.application.useCases.dto.CartResponse;
import com.commerce.backend.modules.cart.application.useCases.dto.ConfirmCartRequest;
import com.commerce.backend.modules.cart.application.useCases.dto.DecrementCartItemRequest;
import com.commerce.backend.modules.cart.application.useCases.dto.IncrementCartItemRequest;
import com.commerce.backend.modules.cart.application.useCases.dto.RemoveFromCartRequest;
import com.commerce.backend.core.controller.ApiController;
import com.commerce.backend.modules.order.application.service.CartService;

import javax.validation.Valid;

@RestController
public class CartController extends ApiController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "/cart")
    public ResponseEntity<CartResponse> addToCart(@RequestBody @Valid AddToCartRequest addToCartRequest) {
        CartResponse cartResponse = cartService.addToCart(addToCartRequest.getProductVariantId(), addToCartRequest.getAmount());
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/cart/increment")
    public ResponseEntity<CartResponse> increaseCartItem(@RequestBody @Valid IncrementCartItemRequest incrementCartItemRequest) {
        CartResponse cartResponse = cartService.incrementCartItem(incrementCartItemRequest.getCartItemId(), incrementCartItemRequest.getAmount());
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/cart/decrement")
    public ResponseEntity<CartResponse> decreaseCartItem(@RequestBody @Valid DecrementCartItemRequest decrementCartItemRequest) {
        CartResponse cartResponse = cartService.decrementCartItem(decrementCartItemRequest.getCartItemId(), decrementCartItemRequest.getAmount());
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/cart")
    public ResponseEntity<CartResponse> fetchCart() {
        CartResponse cartResponse = cartService.fetchCart();
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/cart/remove")
    public ResponseEntity<CartResponse> removeFromCart(@RequestBody @Valid RemoveFromCartRequest removeFromCartRequest) {
        CartResponse cartResponse = cartService.removeFromCart(removeFromCartRequest.getCartItemId());
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/cart")
    public ResponseEntity<HttpStatus> emptyCart() {
        cartService.emptyCart();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/cart/confirm")
    public ResponseEntity<Boolean> confirmCart(@RequestBody @Valid ConfirmCartRequest confirmCartRequest) {
        Boolean confirmCart = cartService.confirmCart(confirmCartRequest);
        return new ResponseEntity<>(confirmCart, HttpStatus.OK);
    }

}
