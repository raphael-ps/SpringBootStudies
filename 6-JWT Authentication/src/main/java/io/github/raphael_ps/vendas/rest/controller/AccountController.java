package io.github.raphael_ps.vendas.rest.controller;

import io.github.raphael_ps.vendas.domain.entity.Account;
import io.github.raphael_ps.vendas.domain.repository.AccountRepository;
import io.github.raphael_ps.vendas.exception.InvalidAuthCredentials;
import io.github.raphael_ps.vendas.rest.dto.AuthDTO;
import io.github.raphael_ps.vendas.rest.dto.TokenDTO;
import io.github.raphael_ps.vendas.security.jwt.JwtService;
import io.github.raphael_ps.vendas.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.h2.command.Token;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/account")
public class AccountController {
    private final UserServiceImpl userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer createAccount(@RequestBody @Valid Account newAccount){
        return userService.save(newAccount);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenDTO authenticate(@RequestBody AuthDTO authCredentials){
        try {
            System.out.println(authCredentials.toString());
            Account userAccount = Account.builder()
                    .login(authCredentials.getLogin())
                    .password(authCredentials.getPassword()).build();
            userService.authenticate(userAccount);

            String token = jwtService.generateToken(userAccount);
            return new TokenDTO(userAccount.getLogin(), token);

        } catch (InvalidAuthCredentials e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
