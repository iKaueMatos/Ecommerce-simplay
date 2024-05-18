package com.commerce.backend.modules.product.application.useCases.controller;

import com.commerce.backend.core.controller.PublicApiController;
import com.commerce.backend.modules.product.application.service.ProductColorService;
import com.commerce.backend.modules.product.application.useCases.dto.ProductColorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ColorController extends PublicApiController {

    private final ProductColorService productColorService;

    @Autowired
    public ColorController(ProductColorService productColorService) {
        this.productColorService = productColorService;
    }


    @GetMapping(value = "/colors")
    public ResponseEntity<List<ProductColorResponse>> getAllColors() {
        List<ProductColorResponse> productColors = productColorService.findAll();
        return new ResponseEntity<>(productColors, HttpStatus.OK);
    }
}
