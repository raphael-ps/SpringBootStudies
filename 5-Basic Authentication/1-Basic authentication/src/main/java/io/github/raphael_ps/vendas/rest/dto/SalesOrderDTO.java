package io.github.raphael_ps.vendas.rest.dto;

import io.github.raphael_ps.vendas.validation.NotEmptySet;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderDTO{
    @NotNull(message = "{field.customer-code.required}")
    private Integer customerId;
    @NotEmptySet(message = "{field.cart-order.required}")
    private Set<OrderItemRequestDTO> cart;
    @NotNull(message = "{field.total-amount.required}")
    private BigDecimal totalAmount;
}
