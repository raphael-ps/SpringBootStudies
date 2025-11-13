package io.github.raphael_ps.vendas.exception;

public class InvalidAuthCredentials extends RuntimeException {
    public InvalidAuthCredentials(String message) {
        super(message);
    }
}
