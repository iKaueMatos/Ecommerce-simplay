package com.commerce.backend.product.domain.service;

import com.commerce.backend.core.error.exception.ResourceNotFoundException;
import com.commerce.backend.product.application.converter.ProductCategoryResponseConverter;
import com.commerce.backend.product.application.service.ProductCategoryService;
import com.commerce.backend.product.application.useCases.dto.ProductCategoryResponse;
import com.commerce.backend.product.infra.cache.ProductCategoryCacheService;
import com.commerce.backend.product.infra.entity.ProductCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryCacheService productCategoryCacheService;
    private final ProductCategoryResponseConverter productCategoryResponseConverter;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryCacheService productCategoryCacheService,
                                      ProductCategoryResponseConverter productCategoryResponseConverter) {
        this.productCategoryCacheService = productCategoryCacheService;
        this.productCategoryResponseConverter = productCategoryResponseConverter;
    }


    @Override
    public List<ProductCategoryResponse> findAllByOrderByName() {
        List<ProductCategory> productCategories = productCategoryCacheService.findAllByOrderByName();
        if (productCategories.isEmpty()) {
            throw new ResourceNotFoundException("Could not find product categories");
        }
        return productCategories
                .stream()
                .map(productCategoryResponseConverter)
                .collect(Collectors.toList());
    }
}
