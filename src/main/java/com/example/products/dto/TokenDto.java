package com.example.products.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TokenDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
