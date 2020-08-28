package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class Bank {
	
	private String name;
	private ArrayList<User> users;
	private ArrayList<Account> accounts;
	private HashSet<String> userUUIDs;
	private HashSet<String> accountUUIDs;
	
	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<>();
		this.accounts = new ArrayList<>();
		
		this.userUUIDs = new HashSet<>();
		this.accountUUIDs = new HashSet<>();
	}
	
	public String getNewUserUUID() {
		String uuid;
		int len = 6;
		Random rng = new Random();
		do {
			uuid = "";
			for (int i = 0; i < len; i++) {
				uuid += String.valueOf(rng.nextInt(10));
			}
		} while (userUUIDs.contains(uuid));
		userUUIDs.add(uuid);
		return uuid;
	}
	
	public String getNewAccountUUID() {
		String uuid;
		int len = 10;
		Random rng = new Random();
		do {
			uuid = "";
			for (int i = 0; i < len; i++) {
				uuid += String.valueOf(rng.nextInt(10));
			}
		} while (accountUUIDs.contains(uuid));
		userUUIDs.add(uuid);
		return uuid;
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	public User addUser(String firstName, String lastName, String pin) {
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		return newUser;
	}
	
	public User userLogin(String userID, String pin) {
		for (User u : this.users) {
			if (u.getUUID().equals(userID) && u.validatePin(pin)) {
				return u;
			}
		}
		return null;
	}
	
	public String getName() {
		return this.name;
	}

}
