package com.cg.paymentwallet.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.cg.paymentwallet.dto.Wallet;
import com.cg.paymentwallet.exception.WalletException;
import com.cg.paymentwallet.service.IWalletService;
import com.cg.paymentwallet.service.WalletServiceImpl;

public class App {
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		int choice1 = 0;

		do {
			System.out.println(" ");
			System.out.println("**===Payment Wallet===**");
			System.out.println("1 To Create Account");
			System.out.println("2 To Login");
			System.out.println("3 To Exit ");
			System.out.println("========================");
			choice1 = scanner.nextInt();
			IWalletService service = new WalletServiceImpl();
			switch (choice1) {
			case 1:
				System.out.println("Enter your Phone Number");
				String phNum = scanner.next();
				System.out.println("Enter your Name");
				String name = scanner.next();
				System.out.println("Enter your Email ID");
				String emailId = scanner.next();
				System.out.println("Enter the EntryId to be created");
				String userId = scanner.next();
				System.out.println("Set your Password");
				String pass = scanner.next();
				System.out.println("Enter the Initial Amount to be Deposited");
				double balance = scanner.nextDouble();

				Wallet wallet = new Wallet();
				wallet.setPhNumber(phNum);
				wallet.setName(name);
				wallet.setEmailId(emailId);
				wallet.setUserId(userId);
				wallet.setPassword(pass);
				wallet.setBalance(balance);

				boolean result = false;
				int row = 0;
				try {
					result = service.validateDetails(wallet);
					row = service.createAccount(wallet);
					System.out.println("Hello " + wallet.getName());

				} catch (WalletException e) {

					e.getMessage();
				}
				if (!result) {
					System.out.println("Cannot create account. Try Again");
				} else if (row != 0) {
					System.out.println(row + " account created");

				} else
					System.out.println("Cannot create account! Try Again");
				break;

			case 2:
				System.out.println("Enter your EntryID");
				String id = scanner.next();
				System.out.println("Enter your Password");
				String password = scanner.next();
				String loginId;
				try {
					loginId = service.login(id, password);
					login(loginId);
				} catch (WalletException e) {
					e.getMessage();
				}
				break;

			case 3:
				System.out.println("=======================Thank You=================");
				break;

			default:
				System.out.println("Wrong choice! Try Again");
				break;
			}
		} while (choice1 != 3);

	}

	private static void login(String userId) {
		int choice2 = 0;
		do {
			System.out.println("1 to show balance");
			System.out.println("2 to withdraw money");
			System.out.println("3 to deposit money");
			System.out.println("4 to transfer funds");
			System.out.println("5 to print transaction history");
			System.out.println("6 To LogOut");
			System.out.println("=====================================");
			
			choice2 = scanner.nextInt();
			IWalletService service = new WalletServiceImpl();

			
			switch (choice2) {
			case 1:
				double balance = service.showBalance(userId);
				System.out.println("Your Account Balance is " + balance);
				break;
			case 2:
				System.out.println("Enter the Amount to be Withdrawn");
				double withdrawAmount = scanner.nextDouble();
				if (service.withdraw(userId, withdrawAmount)) {
					System.out.println("Rupees " + withdrawAmount + " Withdrawn from your Wallet");
					System.out.println("Your Updates Account Balance is Rupees " + service.showBalance(userId));
				} else
					System.out.println("Withdrawal failed! Insuffecient Balance");

				break;
			case 3:
				System.out.println("Enter the Amount to be Deposited");
				double depositAmount = scanner.nextDouble();
				if (service.deposit(userId, depositAmount)) {
					System.out.println("Rupees " + depositAmount + " Deposited to your Wallet");
					System.out.println("Your Updated Balance is Rupees " + service.showBalance(userId));
				} else
					System.out.println("Unable to deposit");
				break;
			case 4:
				System.out.println("Enter the UserId of Whom the fund is to be Transfered");
				String receiverUserId = scanner.next();
				System.out.println("Enter the amount to be Transfered");
				double transferAmount = scanner.nextDouble();
				try {
					if (service.fundTransfer(userId, receiverUserId, transferAmount)) {
						System.out.println("Rupees " + transferAmount + " Succesfully Transfered to " + receiverUserId);
						System.out.println("Your Updated Balance is Rupees " + service.showBalance(userId));
					}
				} catch (WalletException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 5:

				ArrayList<String> transactions=new ArrayList<String>();
				System.out.println("==============Transactions Details===============");
				transactions=service.printTransaction(userId);
				for (String trans : transactions) {
					System.out.println(trans);
				} 

				System.out.println("=================================================");
				break;
			case 6:
				System.out.println("==================Logged out======================");
				break;

			default:
				System.out.println("Invalid Entry.....! try Again");
				break;
			}
		} while (choice2 != 6);

	}
	}

