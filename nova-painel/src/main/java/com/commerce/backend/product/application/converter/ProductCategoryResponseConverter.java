package com.commerce.backend.product.application.converter;

import com.commerce.backend.product.application.useCases.dto.ProductCategoryResponse;
import com.commerce.backend.product.domain.model.CategoryDTO;
import com.commerce.backend.product.infra.entity.ProductCategory;

import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class ProductCategoryResponseConverter implements Function<ProductCategory, ProductCategoryResponse> {
    @Override
    public ProductCategoryResponse apply(ProductCategory productCategory) {
        ProductCategoryResponse productCategoryResponse = new ProductCategoryResponse();
        productCategoryResponse.setCategory(CategoryDTO.builder().name(productCategory.getName()).build());
        return productCategoryResponse;
    }
}
