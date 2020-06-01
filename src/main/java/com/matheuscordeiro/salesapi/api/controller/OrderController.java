package com.matheuscordeiro.salesapi.api.controller;

import com.matheuscordeiro.salesapi.api.dto.OrderDTO;
import com.matheuscordeiro.salesapi.domain.entity.Order;
import com.matheuscordeiro.salesapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save( @RequestBody OrderDTO dto ){
        Order order = orderService.save(dto);
        return order.getId();
    }
}
