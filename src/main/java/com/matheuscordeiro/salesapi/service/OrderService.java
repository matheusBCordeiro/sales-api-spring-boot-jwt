package com.matheuscordeiro.salesapi.service;

import com.matheuscordeiro.salesapi.api.dto.OrderDTO;
import com.matheuscordeiro.salesapi.domain.entity.Order;

public interface OrderService {
    Order save(OrderDTO dto);
}
