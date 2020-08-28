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
		
		holder.addAccount(this);
		bank.addAccount(this);
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
}
