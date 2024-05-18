package com.commerce.backend.modules.product.application.service;

import java.util.List;

import com.commerce.backend.modules.product.application.useCases.dto.ProductCategoryResponse;

public interface ProductCategoryService {
    List<ProductCategoryResponse> findAllByOrderByName();
}
