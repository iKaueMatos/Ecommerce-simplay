package com.commerce.backend.product.infra.cache;

import java.util.List;

import com.commerce.backend.product.infra.entity.Product;
import com.commerce.backend.product.infra.entity.ProductCategory;

public interface ProductCacheService {

    Product findByUrl(String url);

    List<Product> findTop8ByOrderByDateCreatedDesc();

    List<Product> getRelatedProducts(ProductCategory productCategory, Long id);

}
