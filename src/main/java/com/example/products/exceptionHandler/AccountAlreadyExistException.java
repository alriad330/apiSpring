package com.example.products.exceptionHandler;

public class AccountAlreadyExistException extends IllegalArgumentException {

    public AccountAlreadyExistException() {
        super();
    }
    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
