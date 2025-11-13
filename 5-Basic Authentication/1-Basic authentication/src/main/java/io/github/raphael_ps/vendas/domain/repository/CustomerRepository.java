package io.github.raphael_ps.vendas.domain.repository;


import io.github.raphael_ps.vendas.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
};