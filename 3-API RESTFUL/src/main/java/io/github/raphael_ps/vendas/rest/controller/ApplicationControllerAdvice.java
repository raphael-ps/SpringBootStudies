package io.github.raphael_ps.vendas.rest.controller;

import io.github.raphael_ps.vendas.exception.BusinessRulesException;
import io.github.raphael_ps.vendas.exception.IllegalOrderStatusException;
import io.github.raphael_ps.vendas.exception.OrderNotFoundException;
import io.github.raphael_ps.vendas.rest.ApiErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BusinessRulesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessRulesException(BusinessRulesException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleOrderNotFoundException(OrderNotFoundException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(IllegalOrderStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleIllegalOrderStatusException(IllegalOrderStatusException ex){
        return new ApiErrors(ex.getMessage());
    }
}
