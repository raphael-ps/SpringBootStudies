package io.github.raphael_ps.vendas.domain.repository;

import io.github.raphael_ps.vendas.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByLogin(String login);

}
