package io.github.raphael_ps.vendas.service.impl;

import io.github.raphael_ps.vendas.domain.entity.OrderItem;
import io.github.raphael_ps.vendas.domain.entity.Product;
import io.github.raphael_ps.vendas.domain.entity.SalesOrder;
import io.github.raphael_ps.vendas.domain.enums.OrderStatus;
import io.github.raphael_ps.vendas.domain.repository.CustomerRepository;
import io.github.raphael_ps.vendas.domain.repository.OrderItemRepository;
import io.github.raphael_ps.vendas.domain.repository.ProductRepository;
import io.github.raphael_ps.vendas.domain.repository.SalesOrderRepository;
import io.github.raphael_ps.vendas.exception.BusinessRulesException;
import io.github.raphael_ps.vendas.exception.IllegalOrderStatusException;
import io.github.raphael_ps.vendas.exception.OrderNotFoundException;
import io.github.raphael_ps.vendas.rest.dto.*;
import io.github.raphael_ps.vendas.service.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    private final SalesOrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public SalesOrder saveOrder(SalesOrderDTO orderDTO) {
        SalesOrder newOrder = new SalesOrder();
        newOrder.setCreatedAt(LocalDate.now());
        newOrder.setTotalAmount(orderDTO.getTotalAmount());
        newOrder.setStatus(OrderStatus.PENDING);
        customerRepository.findById(orderDTO.getCustomerId())
                .ifPresentOrElse(newOrder::setCustomer, () -> {
                    throw new BusinessRulesException("User not found!");
                });

        Set<OrderItem> cartItems = convertCartItems(newOrder, orderDTO.getCart());

        orderRepository.save(newOrder);
        orderItemRepository.saveAll(cartItems);
        newOrder.setCart(cartItems);

        return newOrder;
    }


    @Override
    public SalesOrderResponseDTO getDetailedOrder(Integer id) {
        SalesOrder order = orderRepository.findOrderFetchItems(id)
                .orElseThrow(()->new OrderNotFoundException("Order code not found!"));

        return this.convertOrderToResponseDTO(order);
    }

    @Override
    @Transactional
    public void updateStatus(Integer orderId, UpdateOrderStatusDTO statusDTO) {
        orderRepository.findById(orderId).ifPresentOrElse(
                order -> order.setStatus(convertToOrderStatus(statusDTO.newStatus())),
                () -> { throw new OrderNotFoundException("This order code doesn't exist."); }
        );
    }

    private OrderStatus convertToOrderStatus(String status){
        try{
            return OrderStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalOrderStatusException("Order status argument is not valid.");
        }
    }

    private SalesOrderResponseDTO convertOrderToResponseDTO(SalesOrder order){
        return SalesOrderResponseDTO.builder()
                .code(order.getId())
                .totalAmount(order.getTotalAmount())
                .customerName(order.getCustomer().getName())
                .customerCpf(order.getCustomer().getCpf())
                .status(order.getStatus().toString())
                .createdAt(order.getCreatedAt().format(DateTimeFormatter.ofPattern("EEEE dd LLLL yyyy")))
                .cart(this.convertItemToResponseDTO(order.getCart()))
                .build();
    }

    private List<OrderItemResponseDTO> convertItemToResponseDTO(Set<OrderItem> cart){

        if (CollectionUtils.isEmpty(cart)){
            return Collections.emptyList();
        }

        return cart.stream().map( item -> OrderItemResponseDTO.builder()
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .productPrice(item.getProduct().getPrice())
                .build()
        ).collect(Collectors.toList());
    }

    private Set<OrderItem> convertCartItems(SalesOrder order, Set<OrderItemRequestDTO> items){
        BigDecimal amount = new BigDecimal(0);

        if (items.isEmpty()){
            throw new BusinessRulesException("The order cart can't be empty");
        }

        return items.stream()
                .map( itemDTO -> {
                    Product product = productRepository.findById(itemDTO.getProductId())
                            .orElseThrow(() -> new BusinessRulesException("Product code doesn't exist."));

                    OrderItem newItem = new OrderItem();
                    newItem.setQuantity(itemDTO.getQuantity());
                    newItem.setProduct(product);
                    newItem.setOrder(order);
                    return newItem;
                }).collect(Collectors.toSet());
    }
}
