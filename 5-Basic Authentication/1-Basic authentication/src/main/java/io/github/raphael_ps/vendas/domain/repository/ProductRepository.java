package io.github.raphael_ps.vendas.domain.repository;

import io.github.raphael_ps.vendas.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "Select p from Product p where p.name like %:name%")
    public List<Product> customQueryByName(@Param("name") String name);

    @Query(value = "SELECT * FROM PRODUCT P WHERE P.NAME LIKE CONCAT('%', :name, '%')", nativeQuery = true)
    public List<Product> customQueryByName2(@Param("name") String name);

    @Query(value = " delete from Product p where p.name = :name")
    @Modifying
    @Transactional
    public void customQueryDeleteByName(@Param("name") String name);
}
