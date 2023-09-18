package com.coderscampus.assignment13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.repository.AccountRepository;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;  

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    public Integer getNumberOfAccounts() {
        long numberOfAccounts = accountRepository.count();  // count() method provided by JpaRepository
        return (int) numberOfAccounts;
    }
}
