package com.commerce.backend.order.domain.model;

import com.commerce.backend.product.domain.model.CategoryDTO;
import com.commerce.backend.product.domain.model.ColorDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderDetailDTO {
    private String url;
    private String name;
    private Float price;
    private Float cargoPrice;
    private String thumb;
    private Integer amount;
    private CategoryDTO category;
    private ColorDTO color;
}
