package io.github.raphael_ps.vendas.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", length = 100)
    @NotEmpty(message = "{field.name.required}")
    private String name;

    @Column(name = "cpf", length = 11, unique = true)
    @CPF(message = "{field.cpf.required}")
    @NotEmpty(message = "{field.cpf.invalid}")
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<SalesOrder> customerOrders;

}
