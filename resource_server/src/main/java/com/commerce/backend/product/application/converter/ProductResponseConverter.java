package com.commerce.backend.product.application.converter;


import com.commerce.backend.product.application.useCases.dto.ProductResponse;
import com.commerce.backend.product.domain.model.ProductVariantDTO;
import com.commerce.backend.product.infra.entity.Product;

import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class ProductResponseConverter implements Function<Product, ProductResponse> {

    @Override
    public ProductResponse apply(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setUrl(product.getUrl());
        productResponse.setProductVariants(product.getProductVariantList()
                .stream()
                .map(variant -> ProductVariantDTO
                        .builder()
                        .id(variant.getId())
                        .price(variant.getPrice())
                        .thumb(variant.getThumb())
                        .stock(variant.getStock())
                        .color(ColorDTO
                                .builder()
                                .name(variant.getColor().getName())
                                .hex(variant.getColor().getHex())
                                .build())
                        .build())
                .collect(Collectors.toList()));

        return productResponse;
    }
}
