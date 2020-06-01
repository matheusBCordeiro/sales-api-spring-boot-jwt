package com.matheuscordeiro.salesapi.domain.repository;

import com.matheuscordeiro.salesapi.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
