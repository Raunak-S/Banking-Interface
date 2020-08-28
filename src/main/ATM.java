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
		
		System.out.println("Menu Option:");
		System.out.println("\t1) List Account Transactions");
		System.out.println("\t2) Withdraw Funds");
		System.out.println("\t3) Deposit Funds");
		System.out.println("\t4) Transfer Funds");
		System.out.println("\t5) Quit");
		System.out.print("Enter choice: ");
		String command = sc.nextLine();
		
		if (command.equals("1")) ATM.showTransactionHistory(currentUser, sc);
		else if (command.equals("2")) ATM.withdraw(currentUser, sc);
	}
	
	public static void showTransactionHistory(User currentUser, Scanner sc) {
		ArrayList<String> accountNames = currentUser.getAccounts();
		ATM.printAccountNames(currentUser);
		int accountNum;
		do {
			System.out.printf("Enter the number (1-%d) of the account whose transactions you want to see: ", accountNames.size());
			accountNum = sc.nextInt()-1;
			
			if (accountNum < 0 || accountNum >= accountNames.size()) {
				System.out.println("Invalid account. Please try again.");
			}
			
		} while (accountNum < 0 || accountNum >= accountNames.size());
		
		Account account = currentUser.getAccount(accountNum);
		System.out.printf("Transaction history for account %s\n", account.getUUID());
		for (Transaction t : account.getTransactions()) {
			System.out.printf("\tDate: %s\n", t.getTimestamp());
			System.out.printf("\tMemo: %s\n", t.getMemo());
			System.out.printf("\tAmount: %.02f\n", t.getAmount());
		}
	}
	
	public static void printAccountNames(User currentUser) {
		ArrayList<String> accountNames = currentUser.getAccounts();
		System.out.println("Open Accounts:");
		for (int i = 0; i < accountNames.size(); i++) {
			System.out.println(String.format("\t%d) %s\n", i, accountNames.get(i)));
		}
	}
	
	public static void withdraw(User currentUser, Scanner sc) {
		ArrayList<String> accountNames = currentUser.getAccounts();
		ATM.printAccountNames(currentUser);
		int accountNum;
		do {
			System.out.printf("Enter the number (1-%d) of the account whose transactions you want to see: ", accountNames.size());
			accountNum = sc.nextInt()-1;
			
			if (accountNum < 0 || accountNum >= accountNames.size()) {
				System.out.println("Invalid account. Please try again.");
			}
			
		} while (accountNum < 0 || accountNum >= accountNames.size());
		
		Account account = currentUser.getAccount(accountNum);
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
