package io.github.raphael_ps.vendas.domain.repository;

import io.github.raphael_ps.vendas.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
