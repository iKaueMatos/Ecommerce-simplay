package com.commerce.backend.modules.product.infra.cache;


import java.util.List;

import com.commerce.backend.modules.product.infra.entity.ProductVariant;

public interface ProductVariantCacheService {
    ProductVariant findById(Long id);
    List<ProductVariant> findTop8ByOrderBySellCountDesc();
}
