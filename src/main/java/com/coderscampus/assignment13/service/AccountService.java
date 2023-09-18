package com.coderscampus.assignment13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import com.coderscampus.assignment13.repository.AccountRepository;

@Service
public class AccountService {

	private final AccountRepository accountRepo;
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserService userService;

	@Autowired
	public AccountService(AccountRepository accountRepository) {
		this.accountRepo = accountRepository;
	}

	public Integer getNumberOfAccounts() {
		long numberOfAccounts = accountRepo.count(); // count() method provided by JpaRepository
		return (int) numberOfAccounts;
	}

	public Account saveAccount(Account account, Long userId) {
		if (account.getAccountId() == null) {
			User user = userService.findById(userId);
			user.getAccounts().add(account);
			account.getUsers().add(user);
			userRepo.save(user);
		}
		return accountRepo.save(account);

	}
}
