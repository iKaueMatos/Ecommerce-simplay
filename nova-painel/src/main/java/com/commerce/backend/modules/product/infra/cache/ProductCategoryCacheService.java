package com.commerce.backend.modules.product.infra.cache;

import java.util.List;

import com.commerce.backend.modules.product.infra.entity.ProductCategory;

public interface ProductCategoryCacheService {
    List<ProductCategory> findAllByOrderByName();
}
