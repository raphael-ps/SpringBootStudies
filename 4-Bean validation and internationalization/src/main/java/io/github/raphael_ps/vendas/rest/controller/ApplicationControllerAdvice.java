package io.github.raphael_ps.vendas.rest.controller;

import io.github.raphael_ps.vendas.exception.BusinessRulesException;
import io.github.raphael_ps.vendas.exception.IllegalOrderStatusException;
import io.github.raphael_ps.vendas.exception.OrderNotFoundException;
import io.github.raphael_ps.vendas.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        return new ApiErrors(
                ex.getBindingResult()
                        .getAllErrors()
                        .stream().map( error -> error.getDefaultMessage())
                        .collect(Collectors.toList())
        );
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
        return new ApiErrors("Informação já existe e não pode ser duplicada.");
    }
}
