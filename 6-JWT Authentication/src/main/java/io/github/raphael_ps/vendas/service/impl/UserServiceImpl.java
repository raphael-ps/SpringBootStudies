package io.github.raphael_ps.vendas.service.impl;

import io.github.raphael_ps.vendas.domain.entity.Account;
import io.github.raphael_ps.vendas.domain.repository.AccountRepository;
import io.github.raphael_ps.vendas.exception.InvalidAuthCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Transactional
    public Integer save(Account newAccount){
        newAccount.setPassword(passwordEncoder.encode(newAccount.getPassword()));
        accountRepository.save(newAccount);
        return newAccount.getId();
    }

    public UserDetails authenticate(Account account) {
        UserDetails userAccount = loadUserByUsername(account.getLogin());
        boolean passwordsMatches = passwordEncoder.matches(account.getPassword(), userAccount.getPassword());

        if (passwordsMatches){
            return userAccount;
        }

        throw new InvalidAuthCredentials("Nome de usuário ou senha inválidos.");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository
                .findByLogin(username)
                .orElseThrow();

        return User.builder()
                .username(account.getLogin())
                .password(account.getPassword())
                .roles(account.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"})
                .build();
    }
}
