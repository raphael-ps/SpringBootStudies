package io.github.raphael_ps.vendas.domain.repository;

import io.github.raphael_ps.vendas.domain.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

}
