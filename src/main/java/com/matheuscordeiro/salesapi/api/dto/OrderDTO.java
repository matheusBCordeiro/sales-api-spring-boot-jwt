package com.matheuscordeiro.salesapi.api.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer client;
    private BigDecimal total;
    private List<OrderItemDTO> items;
}
