package com.commerce.backend.modules.product.application.converter;

import com.commerce.backend.modules.product.application.useCases.dto.ProductColorResponse;
import com.commerce.backend.modules.product.domain.model.ColorDTO;
import com.commerce.backend.modules.product.infra.entity.Color;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductColorResponseConverter implements Function<Color, ProductColorResponse> {
    @Override
    public ProductColorResponse apply(Color color) {
        ProductColorResponse productColorResponse = new ProductColorResponse();
        productColorResponse.setColor(ColorDTO.builder().name(color.getName()).hex(color.getHex()).build());
        return productColorResponse;
    }
}
