package com.matheuscordeiro.salesapi.service.impl;

import com.matheuscordeiro.salesapi.api.dto.OrderDTO;
import com.matheuscordeiro.salesapi.api.dto.OrderItemDTO;
import com.matheuscordeiro.salesapi.domain.OrderStatus;
import com.matheuscordeiro.salesapi.domain.entity.*;
import com.matheuscordeiro.salesapi.domain.repository.*;
import com.matheuscordeiro.salesapi.exception.BusinessException;
import com.matheuscordeiro.salesapi.exception.OrderNotFoundException;
import com.matheuscordeiro.salesapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Order save(OrderDTO dto) {
        Integer idClient = dto.getClient();
        Client client = clientRepository
                .findById(idClient)
                .orElseThrow(() -> new BusinessException("Client code invalid."));

        Order order = new Order();
        order.setTotal(dto.getTotal());
        order.setDataOrder(LocalDate.now());
        order.setClient(client);

        List<OrderItem> orderItems = convertItems(order, dto.getItems());
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        order.setItens(orderItems);
        return order;
    }

    @Override
    public Optional<Order> getFullOrder(Integer id) {
        return orderRepository.findByIdFetchItems(id);
    }

    @Override
    public void statusUpdates(Integer id, OrderStatus orderStatus) {
        orderRepository
                .findById(id)
                .map( order -> {
                    order.setOrderStatus(orderStatus);
                    return orderRepository.save(order);
                }).orElseThrow(() -> new OrderNotFoundException() );
    }

    private List<OrderItem> convertItems(Order order, List<OrderItemDTO> items){
        if(items.isEmpty()){
            throw new BusinessException("It is not possible to place an order without items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduct = dto.getProduct();
                    Product product = productRepository
                            .findById(idProduct)
                            .orElseThrow(
                                    () -> new BusinessException(
                                            "Product code invalid: "+ idProduct
                                    ));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(dto.getQuantity());
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    return orderItem;
                }).collect(Collectors.toList());

    }
}
