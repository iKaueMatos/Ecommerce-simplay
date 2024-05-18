package com.commerce.backend.modules.product.application.converter;

import com.commerce.backend.modules.product.application.useCases.dto.ProductVariantResponse;
import com.commerce.backend.modules.product.domain.model.ColorDTO;
import com.commerce.backend.modules.product.domain.model.ProductVariantDTO;
import com.commerce.backend.modules.product.infra.entity.ProductVariant;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductVariantResponseConverter implements Function<ProductVariant, ProductVariantResponse> {
    @Override
    public ProductVariantResponse apply(ProductVariant productVariant) {
        ProductVariantResponse productVariantResponse = new ProductVariantResponse();
        productVariantResponse.setId(productVariant.getId());
        productVariantResponse.setName(productVariant.getProduct().getName());
        productVariantResponse.setUrl(productVariant.getProduct().getUrl());
        productVariantResponse.setProductVariant(ProductVariantDTO
                .builder()
                .id(productVariant.getId())
                .price(productVariant.getPrice())
                .thumb(productVariant.getThumb())
                .stock(productVariant.getStock())
                .color(ColorDTO
                        .builder()
                        .name(productVariant.getColor().getName())
                        .hex(productVariant.getColor().getHex())
                        .build())
                .build()
        );

        return productVariantResponse;
    }
}
