package com.commerce.backend.modules.order.application.useCases.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.backend.core.controller.ApiController;
import com.commerce.backend.modules.order.application.service.DiscountService;
import com.commerce.backend.modules.order.application.useCases.dto.ApplyDiscountRequest;

import javax.validation.Valid;


@RestController
public class DiscountController extends ApiController {

    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping(value = "/cart/discount")
    public ResponseEntity<HttpStatus> applyDiscount(@RequestBody @Valid ApplyDiscountRequest applyDiscountRequest) {
        discountService.applyDiscount(applyDiscountRequest.getCode());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
