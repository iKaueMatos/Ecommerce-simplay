package com.commerce.backend.product.application.useCases.dto;

import com.commerce.backend.product.domain.model.ColorDTO;

import lombok.Data;

@Data
public class ProductColorResponse {
    private ColorDTO color;
}
