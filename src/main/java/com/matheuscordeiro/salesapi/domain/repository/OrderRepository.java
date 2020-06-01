package com.matheuscordeiro.salesapi.domain.repository;

import com.matheuscordeiro.salesapi.domain.entity.Client;
import com.matheuscordeiro.salesapi.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Integer> {
    List<Order> findByClient(Client client);
}
