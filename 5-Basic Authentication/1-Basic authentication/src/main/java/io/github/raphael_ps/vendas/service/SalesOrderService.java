package io.github.raphael_ps.vendas.service;


import io.github.raphael_ps.vendas.domain.entity.SalesOrder;
import io.github.raphael_ps.vendas.domain.enums.OrderStatus;
import io.github.raphael_ps.vendas.rest.dto.SalesOrderDTO;
import io.github.raphael_ps.vendas.rest.dto.SalesOrderResponseDTO;
import io.github.raphael_ps.vendas.rest.dto.UpdateOrderStatusDTO;

public interface SalesOrderService {

    public SalesOrder saveOrder(SalesOrderDTO order);
    public SalesOrderResponseDTO getDetailedOrder(Integer id);
    public void updateStatus(Integer orderId, UpdateOrderStatusDTO statusDTO);
}
