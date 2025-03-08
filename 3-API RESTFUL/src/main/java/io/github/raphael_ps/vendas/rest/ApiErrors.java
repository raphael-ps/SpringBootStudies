package io.github.raphael_ps.vendas.rest;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
public class ApiErrors {

    private final LocalDateTime timestamp;
    private final List<String> errors;

    public ApiErrors(String errorMsg) {
        this.timestamp = LocalDateTime.now();
        this.errors = Arrays.asList(errorMsg);
    }


}
