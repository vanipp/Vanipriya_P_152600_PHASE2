package com.cg.paymentwallet.service;

import java.util.ArrayList;
import com.cg.paymentwallet.dto.Wallet;
import com.cg.paymentwallet.exception.WalletException;

public interface IWalletService {

	public int createAccount(Wallet wallet) throws WalletException;

	public double showBalance(String phNumber);

	public boolean deposit(String phNumber, double amount);

	public boolean withdraw(String phNumber, double amount);

	public boolean fundTransfer(String phSender, String phReceiver, double amount) throws WalletException;

	public boolean validateDetails(Wallet wallet) throws WalletException;

	public String login(String id, String password) throws WalletException;

	public ArrayList<String> printTransaction(String userId);
}