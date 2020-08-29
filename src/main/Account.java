package main;

import java.util.ArrayList;

public class Account {

	private String name;
	private double balance;
	private String uuid;
	private User holder;
	private ArrayList<Transaction> transactions;
	
	public Account(String name, User holder, Bank bank) {
		this.name = name;
		this.holder = holder;
		this.uuid = bank.getNewAccountUUID();
		this.transactions = new ArrayList<>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Transaction> getTransactions() {
		return this.transactions;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public void withdraw(double amount) {
		this.balance -= amount;
	}
	
	public void deposit(double amount) {
		this.balance += amount;
	}
	
	public void addTransaction(Transaction newTransaction) {
		this.transactions.add(newTransaction);
	}
}
