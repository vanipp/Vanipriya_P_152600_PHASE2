package com.cg.paymentwallet.service.test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cg.paymentwallet.dao.IWalletDao;
import com.cg.paymentwallet.dao.WalletDaoImpl;
import com.cg.paymentwallet.dto.Wallet;
import com.cg.paymentwallet.exception.WalletException;
import com.cg.paymentwallet.service.IWalletService;
import com.cg.paymentwallet.service.WalletServiceImpl;

import junit.framework.TestCase;

public class Validation {
	IWalletService service=new WalletServiceImpl();
	IWalletDao dao=new WalletDaoImpl();
		@Test
		public void CheckForZeroDeposittest() throws WalletException {
			boolean condition=false;
			Wallet wallet=new Wallet();
			wallet.setUserId("9790963670");
			dao.createAccount(wallet);
			condition=service.deposit("9790963670", 0.0);
			assertFalse(condition);
		}
		
		@Test
		public void CheckForValidDepositAmount() throws WalletException {
			boolean condition=false;
			Wallet wallet=new Wallet();
			wallet.setUserId("9790963670");
			dao.createAccount(wallet);
			condition=service.deposit("9790963670", 200);
			assertTrue(condition);
		}
		
		@Test (expected=WalletException.class)
		public void CheckForInvalidNameTest() throws WalletException {
			Wallet wallet=new Wallet();
			wallet.setName("fd65f46");
			wallet.setPhNumber("9790963670");
			wallet.setEmailId("vani@gmail.com");
			service.validateDetails(wallet);
		}
		
		@Test
		public void CheckForValidNameTest() throws WalletException {
			Wallet wallet=new Wallet();
			wallet.setName("Vanipriya");
			wallet.setPhNumber("9790963670");
			wallet.setEmailId("vani@gmail.com");
			boolean condition=service.validateDetails(wallet);
			assertTrue(condition);
		}
		
		@Test (expected=WalletException.class)
		public void CheckForInvalidPhoneNumberTest() throws WalletException {
			Wallet wallet=new Wallet();
			wallet.setName("");
			wallet.setPhNumber("979096");
			wallet.setEmailId("abbc@gmail.com");
			boolean condition=service.validateDetails(wallet);
			assertFalse(condition);
		}
		
		@Test
		public void CheckForValidPhoneNumberTest() throws WalletException {
			Wallet wallet=new Wallet();
			wallet.setName("Vanipriya");
			wallet.setPhNumber("9790963670");
			wallet.setEmailId("vani@gmail.com");
			boolean condition=service.validateDetails(wallet);
			assertTrue(condition);
		}
		
		@Test (expected=WalletException.class)
		public void CheckForInvalidEmailTest() throws WalletException {
			Wallet wallet=new Wallet();
			wallet.setName("Vanipriya");
			wallet.setPhNumber("9789789789");
			wallet.setEmailId("4gfgaff");
			boolean condition=service.validateDetails(wallet);
			assertFalse(condition);
		}
		
		@Test
		public void CheckForValidEmailTest() throws WalletException {
			Wallet wallet=new Wallet();
			wallet.setName("Vanipriya");
			wallet.setPhNumber("9790963670");
			wallet.setEmailId("vani@gmail.com");
			boolean condition=service.validateDetails(wallet);
			assertTrue(condition);
		}


	}


