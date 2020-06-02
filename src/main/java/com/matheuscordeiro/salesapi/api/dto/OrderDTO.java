package com.matheuscordeiro.salesapi.api.dto;

import com.matheuscordeiro.salesapi.validation.NotEmptyList;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @NotNull(message = "Enter the customer code")
    private Integer client;

    @NotNull(message = "Total order field is required")
    private BigDecimal total;

    @NotEmptyList(message = "Order cannot be placed without items")
    private List<OrderItemDTO> items;
}
