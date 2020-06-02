package com.matheuscordeiro.salesapi.service;

import com.matheuscordeiro.salesapi.api.dto.OrderDTO;
import com.matheuscordeiro.salesapi.domain.OrderStatus;
import com.matheuscordeiro.salesapi.domain.entity.Order;

import java.util.Optional;

public interface OrderService {
    Order save(OrderDTO dto);
    Optional<Order> getFullOrder(Integer id);
    void statusUpdates(Integer id, OrderStatus orderStatus);
}
