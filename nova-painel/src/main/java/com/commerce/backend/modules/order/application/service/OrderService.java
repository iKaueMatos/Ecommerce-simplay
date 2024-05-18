package com.commerce.backend.modules.order.application.service;

import java.util.List;

import com.commerce.backend.modules.order.application.useCases.dto.OrderResponse;
import com.commerce.backend.modules.order.application.useCases.dto.PostOrderRequest;

public interface OrderService {
    Integer getAllOrdersCount();
    List<OrderResponse> getAllOrders(Integer page, Integer pageSize);
    OrderResponse postOrder(PostOrderRequest postOrderRequest);
}
