package com.example.products.repositories;

import com.example.products.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);
    Account findByEmail(String email);
}
