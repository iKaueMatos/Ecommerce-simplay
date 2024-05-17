package com.commerce.backend.product.application.useCases.dto;

import lombok.Data;

import java.util.List;

import com.commerce.backend.product.domain.model.CategoryDTO;

@Data
public class ProductDetailsResponse {
    private String name;
    private String url;
    private String sku;
    private String longDesc;
    private CategoryDTO category;
    private List<ProductVariantDetailDTO> productVariantDetails;
}