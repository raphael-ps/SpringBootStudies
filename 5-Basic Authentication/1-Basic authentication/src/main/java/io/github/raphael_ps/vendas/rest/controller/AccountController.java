package io.github.raphael_ps.vendas.rest.controller;

import io.github.raphael_ps.vendas.domain.entity.Account;
import io.github.raphael_ps.vendas.domain.repository.AccountRepository;
import io.github.raphael_ps.vendas.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/account")
public class AccountController {
    private final UserServiceImpl userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer createAccount(@RequestBody @Valid Account newAccount){
        return userService.save(newAccount);
    }
}
