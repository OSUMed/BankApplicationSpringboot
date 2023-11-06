package com.srikanth.assignment13.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srikanth.assignment13.domain.Account;
import com.srikanth.assignment13.domain.Address;
import com.srikanth.assignment13.domain.User;
import com.srikanth.assignment13.repository.AccountRepository;
import com.srikanth.assignment13.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private AccountService accountService;

	public List<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	public List<User> findByNameAndUsername(String name, String username) {
		return userRepo.findByNameAndUsername(name, username);
	}

	public List<User> findByCreatedDateBetween(LocalDate date1, LocalDate date2) {
		return userRepo.findByCreatedDateBetween(date1, date2);
	}

	public User findExactlyOneUserByUsername(String username) {
		List<User> users = userRepo.findExactlyOneUserByUsername(username);
		if (users.size() > 0)
			return users.get(0);
		else
			return new User();
	}

	public Set<User> findAll() {
		return userRepo.findAllUsersWithAccountsAndAddresses();
	}

	public User findById(Long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		return userOpt.orElse(new User());
	}

	public User saveUser(User user) {
		if (isNewUser(user)) {
			// Initialize default accounts and address for the new user
			createDefaultAccountsForNewUser(user);
			initializeDefaultAddressForNewUser(user);
			
			// Persist the new user in the repository
			return userRepo.save(user);
		}
		
		// Retrieve the persisted user data from the repository
		Optional<User> savedUser = userRepo.findById(user.getUserId());
		
		// Update user's accounts with the corresponding accounts from the repository
		List<Account> userAccounts = savedUser.get().getAccounts();
		user.setAccounts(userAccounts);
		
		// If the incoming password is empty, set it to the persisted user's password
		if (savedUser.isPresent()) {
			if (user.getPassword().isEmpty()) {
				user.setPassword(savedUser.get().getPassword());
			}
		}

		// Persist the updated user data and return
		return userRepo.save(user);
	}

	private void initializeDefaultAddressForNewUser(User user) {
	    String[] accountTypes = {"Checking Account", "Savings Account"};
	    
	    for (String accountType : accountTypes) {
	        Account account = new Account();
	        account.setAccountName(accountType);
	        linkUserAndAccount(user, account);
	        accountRepo.save(account);
	    }

	}

	private void createDefaultAccountsForNewUser(User user) {
		Address address = new Address();
		address.setAddressLine1("Fake Street");
		linkUserAndAddress(user, address);

	}

	private boolean isNewUser(User user) {
		return user.getUserId() == null;
	}

	private void linkUserAndAccount(User user, Account account) {
		account.getUsers().add(user);
		user.getAccounts().add(account);
	}

	private void linkUserAndAddress(User user, Address address) {
		address.setUser(user);
		user.setAddress(address);
	}

	public void delete(Long userId) {
		userRepo.deleteById(userId);
	}

	public void prepareNewAccount(Account account, User user) {
		// TODO Auto-generated method stub
		Integer accountNumber = user.getAccounts().size() + 1;
		String accountName = "Account #" + accountNumber;
		account.setAccountName(accountName);

	}

}
