package com.commerce.backend.product.infra.cache;

import java.util.List;

import com.commerce.backend.product.infra.entity.Color;

public interface ProductColorCacheService {
    List<Color> findAll();
}
