package io.github.raphael_ps.vendas.rest.controller;

import io.github.raphael_ps.vendas.domain.enums.OrderStatus;
import io.github.raphael_ps.vendas.rest.dto.SalesOrderDTO;
import io.github.raphael_ps.vendas.domain.entity.SalesOrder;
import io.github.raphael_ps.vendas.rest.dto.SalesOrderResponseDTO;
import io.github.raphael_ps.vendas.rest.dto.UpdateOrderStatusDTO;
import io.github.raphael_ps.vendas.service.SalesOrderService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RequestMapping("api/order")
@RestController
public class SalesOrderController {

    private final SalesOrderService orderService;

    public SalesOrderController(SalesOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer resgisterOrder(@RequestBody SalesOrderDTO order){
        return orderService.saveOrder(order).getId();
    }

    @GetMapping("/{id}")
    public SalesOrderResponseDTO findById(@PathVariable Integer id){
        return orderService.getDetailedOrder(id);
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateOrderStatus(@PathVariable Integer id, @RequestBody UpdateOrderStatusDTO statusDTO){
        orderService.updateStatus(id, statusDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateOrder(@PathVariable Integer id, @RequestBody SalesOrder order) {
    }
}





