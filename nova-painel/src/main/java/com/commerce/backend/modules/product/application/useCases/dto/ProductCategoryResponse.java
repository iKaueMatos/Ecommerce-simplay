package com.commerce.backend.modules.product.application.useCases.dto;

import com.commerce.backend.modules.product.domain.model.CategoryDTO;

import lombok.Data;

@Data
public class ProductCategoryResponse {
    private CategoryDTO category;
}