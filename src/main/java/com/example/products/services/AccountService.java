package com.example.products.services;

import com.example.products.models.Account;
import com.example.products.repositories.AccountRepository;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class AccountService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;


    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }

    /**
     * Override method
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);

        if (account != null) {
            var accountUser = User.withUsername(account.getUsername())
                    .password(account.getPassword())
                    .build();
            return accountUser;
        }
        return null;
    }

    public Account getAccountByUserName(String userName) {
        return accountRepository.findByUsername(userName);
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
}
