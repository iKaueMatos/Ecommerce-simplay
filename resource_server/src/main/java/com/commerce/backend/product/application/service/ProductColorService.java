package com.commerce.backend.product.application.service;


import java.util.List;

import com.commerce.backend.product.application.useCases.dto.ProductColorResponse;

public interface ProductColorService {
    List<ProductColorResponse> findAll();
}
