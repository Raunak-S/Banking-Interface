package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ATM {
	
	public static User mainMenuPrompt(Bank bank, Scanner sc) {
		String userID;
		String pin;
		User authUser;
		System.out.println(String.format("Welcome to %s!", bank.getName()));
		do {
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("Enter pin: ");
			pin = sc.nextLine();
			authUser = bank.userLogin(userID, pin);
			if (authUser == null) {
				System.out.println("Incorect userID/pin combination. Please try again.");
			}
		} while (authUser == null);
		return authUser;
	}
	
	public static void printUserMenu(User currentUser, Scanner sc) {
		System.out.println(String.format("Welcome %s!", currentUser.getFirstName()));
		String command;
		do {
			System.out.println("Menu Option:");
			System.out.println("\t1) List Account Transactions");
			System.out.println("\t2) Withdraw Funds");
			System.out.println("\t3) Deposit Funds");
			System.out.println("\t4) Transfer Funds");
			System.out.println("\t5) Quit");
			System.out.print("Enter choice: ");
			command = sc.nextLine();
			if (command.equals("")) {
				command = sc.nextLine();
			}
			
			if (command.equals("1")) ATM.showTransactionHistory(currentUser, sc);
			else if (command.equals("2")) ATM.withdraw(currentUser, sc);
			else if (command.equals("3")) ATM.deposit(currentUser, sc);
			else if (command.equals("4")) ;
			else if (command.equals("5")) ;
		} while (!command.equals("5"));
	
	}
	
	public static void showTransactionHistory(User currentUser, Scanner sc) {
		int accountNum = getAccountNum(currentUser, sc, "Enter the number (1-%d) of the account whose history you want to see: ");
		Account account = currentUser.getAccount(accountNum);
		System.out.printf("Transaction history for account %s\n", account.getUUID());
		for (Transaction t : account.getTransactions()) {
			System.out.printf("\tDate: %s\n", t.getTimestamp());
			System.out.printf("\tMemo: %s\n", t.getMemo());
			System.out.printf("\tAmount: %.02f\n", t.getAmount());
		}
	}
	
	public static int getAccountNum(User currentUser, Scanner sc, String prompt) {
		ArrayList<String> accountNames = currentUser.getAccounts();
		System.out.println("Open Accounts:");
		for (int i = 0; i < accountNames.size(); i++) {
			System.out.println(String.format("\t%d) %s", i+1, accountNames.get(i)));
		}
		int accountNum;
		do {
			System.out.printf(prompt, accountNames.size());
			accountNum = sc.nextInt()-1;
			if (accountNum < 0 || accountNum >= accountNames.size()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (accountNum < 0 || accountNum >= accountNames.size());
		return accountNum;
	}
	
	public static void withdraw(User currentUser, Scanner sc) {
		int accountNum = getAccountNum(currentUser, sc, "Enter the number (1-%d) of the account from which you want to withdraw: ");
		System.out.print("Amount that you would like to withdraw: ");
		double amount = sc.nextDouble();
		Account account = currentUser.getAccount(accountNum);
		Transaction newTransaction = new Transaction(amount, String.format("Withdrawal of $%.02f", amount), account);
		account.withdraw(amount);
		account.addTransaction(newTransaction);
	}
	
	public static void deposit(User currentUser, Scanner sc) {
		int accountNum = getAccountNum(currentUser, sc, "Enter the number (1-%d) of the account to which you want to deposit: ");
		System.out.print("Amount that you would like to deposit: ");
		double amount = sc.nextDouble();
		Account account = currentUser.getAccount(accountNum);
		Transaction newTransaction = new Transaction(amount, String.format("Deposit of $%.02f", amount), account);
		account.deposit(amount);
		account.addTransaction(newTransaction);
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Bank tdBank = new Bank("TD Bank");
		User u1 = tdBank.addUser("John", "Doe", "1234");
		Account newAccount = new Account("Checking", u1, tdBank);
		u1.addAccount(newAccount);
		tdBank.addAccount(newAccount);
		User currentUser;

		while (true) {
			currentUser = ATM.mainMenuPrompt(tdBank, sc);
			ATM.printUserMenu(currentUser, sc);
		}
	}
}
