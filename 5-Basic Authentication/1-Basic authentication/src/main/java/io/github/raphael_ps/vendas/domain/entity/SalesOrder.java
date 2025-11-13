package io.github.raphael_ps.vendas.domain.entity;

import io.github.raphael_ps.vendas.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "sales_order")
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "total_amount", precision = 20, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> cart;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
}
