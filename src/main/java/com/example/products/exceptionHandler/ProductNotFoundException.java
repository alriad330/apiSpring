package com.example.products.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends IllegalArgumentException {

    public ProductNotFoundException() {
        super();
    }
    public ProductNotFoundException (String message) {
        super(message);
    }
}
