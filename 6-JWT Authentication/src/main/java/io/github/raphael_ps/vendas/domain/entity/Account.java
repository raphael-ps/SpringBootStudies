package io.github.raphael_ps.vendas.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login")
    @NotEmpty(message = "{field.login.required}")
    private String login;

    @Column(name = "password")
    @NotEmpty(message = "{field.password.required}")
    private String password;

    @Column(name = "admin")
    private boolean admin;
}
