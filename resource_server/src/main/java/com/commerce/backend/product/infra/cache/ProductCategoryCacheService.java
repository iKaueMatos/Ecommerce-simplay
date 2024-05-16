package com.commerce.backend.product.infra.cache;

import java.util.List;

import com.commerce.backend.product.infra.entity.ProductCategory;

public interface ProductCategoryCacheService {
    List<ProductCategory> findAllByOrderByName();
}
