package com.matheuscordeiro.salesapi.api.controller;

import com.matheuscordeiro.salesapi.api.dto.OrderInformationDTO;
import com.matheuscordeiro.salesapi.api.dto.OrderItemInformationDTO;
import com.matheuscordeiro.salesapi.api.dto.OrderDTO;
import com.matheuscordeiro.salesapi.api.dto.OrderStatusUpdatesDTO;
import com.matheuscordeiro.salesapi.domain.OrderStatus;
import com.matheuscordeiro.salesapi.domain.entity.Order;
import com.matheuscordeiro.salesapi.domain.entity.OrderItem;
import com.matheuscordeiro.salesapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("{id}")
    public OrderInformationDTO getById(@PathVariable Integer id ){
        return orderService
                .getFullOrder(id)
                .map(order -> convert(order))
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND, "Order not found."));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid OrderDTO dto){
        Order order = orderService.save(dto);
        return order.getId();
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable @Valid Integer id ,
                             @RequestBody OrderStatusUpdatesDTO dto){
        String newStatus = dto.getNewStatus();
        orderService.statusUpdates(id, OrderStatus.valueOf(newStatus));
    }

    private OrderInformationDTO convert(Order order){
        return OrderInformationDTO
                .builder()
                .code(order.getId())
                .dateOrder(order.getDataOrder().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(order.getClient().getCpf())
                .clientName(order.getClient().getName())
                .total(order.getTotal())
                .status(order.getOrderStatus().name())
                .items(convert(order.getItens()))
                .build();
    }

    private List<OrderItemInformationDTO> convert(List<OrderItem> items){
        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }
        return items.stream().map(
                item -> OrderItemInformationDTO
                        .builder().productDescription(item.getProduct().getDescription())
                        .unitPrice(item.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .build()
        ).collect(Collectors.toList());
    }
}
