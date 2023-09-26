package com.coderscampus.assignment13.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;

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
		if (isNewUser(user) ) {			
			//Set two default new accounts per new user:
			Account checking = new Account();
			Account savings = new Account();
			checking.setAccountName("Checking Account");
			savings.setAccountName("Savings Account");
			
			// Add bidirectional relationships between User and Account for both new accounts:
			linkUserAndAccount(user, checking);
			linkUserAndAccount(user, savings);
			accountRepo.save(checking);
			accountRepo.save(savings);
			
			Address address = new Address();
			// Add new address and create bidirectional relationship
			address.setAddressLine1("Fake Street");
			linkUserAndAddress(user, address);
			// Cascade deals with the owning side(address) updates
			return userRepo.save(user);

		}
		// Get Repo version of user:
		Optional<User> savedUser = userRepo.findById(user.getUserId());
		List<Account> userAccounts = savedUser.get().getAccounts();
		user.setAccounts(userAccounts);
		System.out.println("savedUser is: " + savedUser);
		
		// Always put userRepo password into incoming password field so password is enforced always:
		// Other cases: we are updating address, so password should get updated
		if (savedUser.isPresent()) {
			if (user.getPassword().isEmpty()) {
				user.setPassword(savedUser.get().getPassword());
			}
		}
	
		return userRepo.save(user);
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
