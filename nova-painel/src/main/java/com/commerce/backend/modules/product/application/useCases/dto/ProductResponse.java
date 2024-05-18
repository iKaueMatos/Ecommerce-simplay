package com.commerce.backend.modules.product.application.useCases.dto;

import lombok.Data;

import java.util.List;

import com.commerce.backend.modules.product.domain.model.ProductVariantDTO;

@Data
public class ProductResponse {
    private String name;
    private String url;
    private List<ProductVariantDTO> productVariants;
}