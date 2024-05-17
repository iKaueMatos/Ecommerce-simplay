package com.commerce.backend.order.application.converter;

import com.commerce.backend.order.application.useCases.dto.OrderResponse;
import com.commerce.backend.order.domain.model.DiscountDTO;
import com.commerce.backend.order.domain.model.OrderDetailDTO;
import com.commerce.backend.order.infra.entity.Order;
import com.commerce.backend.product.domain.model.CategoryDTO;
import com.commerce.backend.product.domain.model.ColorDTO;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrderResponseConverter implements Function<Order, OrderResponse> {
   @Override
    public OrderResponse apply(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setShipName(order.getShipName());
        orderResponse.setShipAddress(order.getShipAddress());
        orderResponse.setBillingAddress(order.getBillingAddress());
        orderResponse.setCity(order.getCity());
        orderResponse.setCountry(order.getCountry());
        orderResponse.setState(order.getState());
        orderResponse.setZip(order.getZip());
        orderResponse.setPhone(order.getPhone());
        orderResponse.setTotalPrice(order.getTotalPrice());
        orderResponse.setTotalCargoPrice(order.getTotalCargoPrice());
        orderResponse.setDate(order.getDate().getTime());
        orderResponse.setShipped(order.getShipped());
        orderResponse.setCargoFirm(order.getCargoFirm());
        orderResponse.setTrackingNumber(order.getTrackingNumber());

        if (Objects.nonNull(order.getDiscount())) {
            orderResponse.setDiscount(DiscountDTO.builder()
                    .discountPercent(order.getDiscount().getDiscountPercent())
                    .status(order.getDiscount().getStatus())
                    .build());
        }

        List<OrderDetailDTO> orderDetailDTOList = order.getOrderDetailList().stream()
                .map(orderDetails -> OrderDetailDTO.builder()
                        .url(orderDetails.getProductVariant().getProduct().getUrl())
                        .name(orderDetails.getProductVariant().getProduct().getName())
                        .price(orderDetails.getProductVariant().getPrice())
                        .cargoPrice(orderDetails.getProductVariant().getCargoPrice())
                        .thumb(orderDetails.getProductVariant().getThumb())
                        .amount(orderDetails.getAmount())
                        .category(CategoryDTO.builder()
                                .name(orderDetails.getProductVariant().getProduct().getProductCategory().getName())
                                .build())
                        .color(ColorDTO.builder()
                                .name(orderDetails.getProductVariant().getColor().getName())
                                .hex(orderDetails.getProductVariant().getColor().getHex())
                                .build())
                        .build())
                .collect(Collectors.toList());

        orderResponse.setOrderItems(orderDetailDTOList);

        return orderResponse;
    }
}
