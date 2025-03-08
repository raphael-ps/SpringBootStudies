package io.github.raphael_ps.vendas.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class SalesOrderResponseDTO {
    private Integer code;
    private String customerName;
    private BigDecimal totalAmount;
    private List<OrderItemResponseDTO> cart;
    private String createdAt;
    private String status;
}
