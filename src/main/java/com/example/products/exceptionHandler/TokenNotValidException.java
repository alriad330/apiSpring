package com.example.products.exceptionHandler;

public class TokenNotValidException extends IllegalArgumentException {

    public TokenNotValidException() {
        super();
    }
    public TokenNotValidException(String message) {
        super(message);
    }
}
