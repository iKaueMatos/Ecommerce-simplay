package com.commerce.backend.modules.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DiscountDTO {
    private Integer discountPercent;
    private Integer status;
}
