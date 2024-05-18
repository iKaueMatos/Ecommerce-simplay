package com.commerce.backend.modules.product.application.useCases.dto;

import com.commerce.backend.modules.product.domain.model.ColorDTO;

import lombok.Data;

@Data
public class ProductColorResponse {
    private ColorDTO color;
}
