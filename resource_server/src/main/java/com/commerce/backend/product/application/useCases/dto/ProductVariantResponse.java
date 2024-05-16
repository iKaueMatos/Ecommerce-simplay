package com.commerce.backend.product.application.useCases.dto;

import com.commerce.backend.product.domain.model.ProductVariantDTO;

import lombok.Data;


@Data
public class ProductVariantResponse {
    private Long id;
    private String name;
    private String url;
    private ProductVariantDTO productVariant;
}
