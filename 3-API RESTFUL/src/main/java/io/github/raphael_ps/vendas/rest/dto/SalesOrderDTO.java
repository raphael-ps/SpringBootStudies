package io.github.raphael_ps.vendas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderDTO{
    private Integer customerId;
    private Set<OrderItemRequestDTO> cart;
    private BigDecimal totalAmount;
}
