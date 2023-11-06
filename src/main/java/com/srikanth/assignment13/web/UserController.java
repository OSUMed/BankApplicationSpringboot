package com.srikanth.assignment13.web;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.srikanth.assignment13.domain.Account;
import com.srikanth.assignment13.domain.User;
import com.srikanth.assignment13.service.AccountService;
import com.srikanth.assignment13.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@GetMapping("/register")
	public String getCreateUser(ModelMap model) {
		model.put("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String postCreateUser(User user) {
		System.out.println(user);
		userService.saveUser(user);
		accountService.setAccountAmount(accountService.getAccountAmount() + 2);
		return "redirect:/register";
	}

	@GetMapping("/users")
	public String getAllUsers(ModelMap model) {
		Set<User> users = userService.findAll();
		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
			Integer nextAccountNumber = accountService.getNextAccountNumber();
			model.put("new_account_number", nextAccountNumber);
		}
		return "users";
	}

	@GetMapping("/users/{userId}")
	public String getOneUser(ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		model.put("user", user);
		Integer nextAccountNumber = accountService.getNextAccountNumber();
		model.put("new_account_number", nextAccountNumber);
		return "user";
	}

	@PostMapping("/users/{userId}")
	public String postOneUser(User user) {
		userService.saveUser(user);
		return "redirect:/users/" + user.getUserId();
	}

	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser(@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}

	@GetMapping("/users/{userId}/accounts/{accountId}")
	public String getAUserAccountPage(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
		User user = userService.findById(userId);
		Account account = accountService.getAccount(accountId);
		if (account.getAccountId() == null) {
			userService.prepareNewAccount(account, user);
		}
		model.put("account", account);
		model.put("user", user);
		model.put("new_account_number", accountId);
		return "account";
	}

	@PostMapping("/users/{userId}/accounts/{accountId}")
	public String updateAccountToUser(Account account, @PathVariable Long userId, @PathVariable Long accountId) {
		accountService.saveAccount(account, userId);
		return "redirect:/users/" + userId + "/accounts/" + accountId;
	}

	@PostMapping("/users/{userId}/accounts")
	public String addAccountToUser(Account account, @PathVariable Long userId) {
		accountService.saveAccount(account, userId);
		return "redirect:/users/" + userId;
	}
}
