package com.commerce.backend.modules.product.infra.cache;

import java.util.List;

import com.commerce.backend.modules.product.infra.entity.Color;

public interface ProductColorCacheService {
    List<Color> findAll();
}
