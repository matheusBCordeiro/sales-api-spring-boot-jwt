package com.matheuscordeiro.salesapi.api.dto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInformationDTO {
    private Integer code;
    private String cpf;
    private String clientName;
    private BigDecimal total;
    private String dateOrder;
    private String status;
    private List<OrderItemInformationDTO> items;
}
