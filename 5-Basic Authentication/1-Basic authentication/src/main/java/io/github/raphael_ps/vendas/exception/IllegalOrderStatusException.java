package io.github.raphael_ps.vendas.exception;

public class IllegalOrderStatusException extends RuntimeException {
    public IllegalOrderStatusException(String message) {
        super(message);
    }
}
