package com.coderscampus.assignment13.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;

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
		long numberOfAccounts = accountRepo.count();
		return (int) numberOfAccounts;
	}

	/**
	 * Saves the given account and associates it with a user if it's a new account.
	 *
	 * @param account the account to be saved
	 * @param userId  the ID of the user to associate with the account if it's new
	 * @return the saved account
	 */
	public Account saveAccount(Account account, Long userId) {
		User user = userService.findById(userId);

		// If new account, create relationship, then save. Else, save
		if (isNewAccount(account)) {
			linkAccountAndUser(account, user);
			account = accountRepo.save(account);
			userRepo.save(user);

		} else {
			account = accountRepo.save(account);
			userRepo.save(user);
		}

		return account;

	}

	private void linkAccountAndUser(Account account, User user) {
		account.getUsers().add(user);
		user.getAccounts().add(account);
	}

	private boolean isNewAccount(Account account) {
		return account.getAccountId() == null;
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

	// Calculate the next available account number, or return 1 if
	// no accounts exist yet
	public Integer getNextAccountNumber() {
		return accountRepo.findMaxAccountId().map(maxAccountId -> maxAccountId.intValue() + 1).orElse(1);
	}

}
