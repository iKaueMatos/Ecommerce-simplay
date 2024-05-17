package com.commerce.backend.product.application.useCases.dto;

import com.commerce.backend.product.domain.model.CategoryDTO;

import lombok.Data;

@Data
public class ProductCategoryResponse {
    private CategoryDTO category;
}