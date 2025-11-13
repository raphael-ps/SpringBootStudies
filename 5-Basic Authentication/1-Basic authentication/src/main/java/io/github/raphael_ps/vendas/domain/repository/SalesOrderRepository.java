package io.github.raphael_ps.vendas.domain.repository;

import io.github.raphael_ps.vendas.domain.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {

    @Query("SELECT s FROM SalesOrder s LEFT JOIN FETCH s.cart WHERE s.id = :orderId")
    public Optional<SalesOrder> findOrderFetchItems(@Param("orderId") Integer orderId);
}
