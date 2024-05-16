package com.commerce.backend.product.application.converter;

import com.commerce.backend.product.application.useCases.dto.ProductDetailsResponse;
import com.commerce.backend.product.application.useCases.dto.ProductVariantDetailDTO;
import com.commerce.backend.product.domain.model.CategoryDTO;
import com.commerce.backend.product.domain.model.ColorDTO;
import com.commerce.backend.product.infra.entity.Product;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProductDetailsResponseConverter implements Function<Product, ProductDetailsResponse> {
        @Override
        public ProductDetailsResponse apply(Product product) {
                ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
                productDetailsResponse.setName(product.getName());
                productDetailsResponse.setUrl(product.getUrl());
                productDetailsResponse.setLongDesc(product.getLongDesc());
                productDetailsResponse.setSku(product.getSku());

                CategoryDTO categoryDTO = CategoryDTO.builder()
                                .name(product.getProductCategory().getName())
                                .build();
                productDetailsResponse.setCategory(categoryDTO);

                List<ProductVariantDetailDTO> productVariantDetailDTOs = product.getProductVariantList()
                                .stream()
                                .map(productVariant -> ProductVariantDetailDTO.builder()
                                                .id(productVariant.getId())
                                                .width(productVariant.getWidth())
                                                .height(productVariant.getHeight())
                                                .composition(productVariant.getComposition())
                                                .price(productVariant.getPrice())
                                                .cargoPrice(productVariant.getCargoPrice())
                                                .taxPercent(productVariant.getTaxPercent())
                                                .image(productVariant.getImage())
                                                .thumb(productVariant.getThumb())
                                                .stock(productVariant.getStock())
                                                .live(productVariant.getLive())
                                                .color(ColorDTO.builder()
                                                                .name(productVariant.getColor().getName())
                                                                .hex(productVariant.getColor().getHex())
                                                                .build())
                                                .build())
                                .collect(Collectors.toList());

                productDetailsResponse.setProductVariantDetails(productVariantDetailDTOs);

                return productDetailsResponse;
        }
}
