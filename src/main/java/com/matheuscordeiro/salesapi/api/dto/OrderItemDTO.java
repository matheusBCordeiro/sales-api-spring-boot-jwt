package com.matheuscordeiro.salesapi.api.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Integer product;
    private Integer quantity;
}
