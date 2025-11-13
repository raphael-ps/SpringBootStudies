package io.github.raphael_ps.vendas.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemResponseDTO {
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity;

}
