package main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class User {
	
	private String firstName;
	private String lastName;
	
	private String uuid;
	
	private byte pinHash[];
	
	private ArrayList<Account> accounts;
	
	public User(String firstName, String lastName, String pin, Bank bank) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
		this.uuid = bank.getNewUserUUID();
		
		this.accounts = new ArrayList<>();
		
		System.out.println(String.format("New user (%s, %s) with ID %s created.", this.lastName, this.firstName, this.uuid));
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	public String getUUID() {
		return uuid;
	}
	
	public boolean validatePin(String pin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return Arrays.equals(this.pinHash, md.digest(pin.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public ArrayList<String> getAccounts() {
		ArrayList<String> accountNames = new ArrayList<>();
		for (Account a : this.accounts) {
			accountNames.add(a.getName());
		}
		return accountNames;
	}
	
	public Account getAccount(int n) {
		return this.accounts.get(n);
	}
	
	
}