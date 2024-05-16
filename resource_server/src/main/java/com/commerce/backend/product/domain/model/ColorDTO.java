package com.commerce.backend.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ColorDTO {
    private String name;
    private String hex;
}
