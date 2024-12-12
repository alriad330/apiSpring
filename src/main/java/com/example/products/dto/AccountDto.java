package com.example.products.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountDto {

    private String username;

    private String firstname;

    private String email;

    @NotEmpty
    @Size(min = 6, message = "Minimum Password length is 6 characters")
    private String password;
}
