package com.coderscampus.assignment13.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import com.coderscampus.assignment13.repository.AccountRepository;

@Service
public class AccountService {
	private Integer accountAmount = 0;

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
		
		
		// If current account Id matches with expected next account id, it is 
		// a new account so do bidirectional relationship adds bw account & users:
		Integer maxAccountId = this.getNextAccountNumber();
		if (account.getAccountId() == null || account.getAccountId().intValue() == maxAccountId) {
			User user = userService.findById(userId);
			account.getUsers().add(user);
			user.getAccounts().add(account);
			account = accountRepo.save(account);
			userRepo.save(user);
			return account;
		}
		
		// else, we are just updating an existing account record:
		User user = userService.findById(userId);
		userRepo.save(user);
		return accountRepo.save(account);

	}

	public Account getAccount(Long accountId) {
		Optional<Account> userOpt = accountRepo.findById(accountId);
		return userOpt.orElse(new Account());
	}

	public Integer getUserAccountNumbers(User user) {
		// TODO Auto-generated method stub
		return user.getAccounts().size();

	}

	public Integer getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(Integer accountAmount) {
		this.accountAmount = accountAmount;
	}

	public Integer getNextAccountNumber() {
		Long maxAccountId = accountRepo.findMaxAccountId();
		return (maxAccountId != null ? maxAccountId.intValue() + 1 : 1);
	}

}
