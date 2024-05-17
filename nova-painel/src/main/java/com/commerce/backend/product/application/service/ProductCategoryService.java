package com.commerce.backend.product.application.service;


import java.util.List;

import com.commerce.backend.product.application.useCases.dto.ProductCategoryResponse;

public interface ProductCategoryService {
    List<ProductCategoryResponse> findAllByOrderByName();
}
