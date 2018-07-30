package com.cg.paymentwallet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import com.cg.paymentwallet.dto.Wallet;
import com.cg.paymentwallet.exception.IWalletException;
import com.cg.paymentwallet.exception.WalletException;
import com.cg.paymentwallet.util.DBUtil;

public class WalletDaoImpl implements IWalletDao {

	public int createAccount(Wallet wallet) throws WalletException {
		Connection con = DBUtil.getConnection();
		
		int row = 0;
		String sql = "INSERT INTO wallet VALUES ( ? , ? , ? , ?, ?, ? ) ";
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, wallet.getName());
			statement.setString(2, wallet.getPhNumber());
			statement.setString(3, wallet.getEmailId());
			statement.setDouble(4, wallet.getBalance());
			statement.setString(5, wallet.getUserId());
			statement.setString(6, wallet.getPassword());

			row = statement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	public double showBalance(String userId) {
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT balance FROM wallet WHERE userid='" + userId + "'";
		double balance = 0;
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet set = statement.executeQuery(sql);

			while (set.next()) {
				balance = set.getInt("balance");
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}

	public void deposit(String userId, double amount) {
		Connection con = DBUtil.getConnection();
		int row=0;
		String sql = "update wallet set balance=balance+" + amount + " where userid='" + userId + "'";

		try {
			PreparedStatement statement = con.prepareStatement(sql);
			row=statement.executeUpdate();
			if(row!=0) {
				String sqlT = "Insert into transaction values (?,?)";
			PreparedStatement statementT = con.prepareStatement(sqlT);
			statementT.setString(1, userId);
			statementT.setString(2, amount + " Deposited ");

			statementT.execute();
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void withdraw(String userId, double amount) {
		Connection con = DBUtil.getConnection();
		int row=0;

		String sql = "update wallet set balance=balance-" + amount + " where userid='" + userId + "'";
		
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			row=statement.executeUpdate();
			if(row!=0) {
				String sqlT = "Insert into transaction values (?,?)";
			PreparedStatement statementT = con.prepareStatement(sqlT);
			statementT.setString(1, userId);
			statementT.setString(2, amount + " Withdrawn ");
			statementT.execute();
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean fundTransfer(String userIdSender, String userIdReceiver, double amount) throws WalletException {
		Connection con = DBUtil.getConnection();
		boolean result = false;
		int row1 = 0;
		int row2 = 0;

		String sql1 = "update wallet set balance=balance+" + amount + " where userid='" + userIdReceiver + "'";

		String sql2 = "update wallet set balance=balance-" + amount + " where userid='" + userIdSender + "'";

		try {
			PreparedStatement stmt1 = con.prepareStatement(sql1);
			PreparedStatement stmt2 = con.prepareStatement(sql2);
			
			row1 = stmt1.executeUpdate();
			row2 = stmt2.executeUpdate();
			
			if ((row1 != 0) && (row2 != 0)) {
				String sql1T = "Insert into transaction values (?,?)";
				PreparedStatement statement1T = con.prepareStatement(sql1T);
				statement1T.setString(1, userIdReceiver);
				statement1T.setString(2, amount + " Received From " + userIdSender);
				statement1T.execute();
				
				String sql2T = "Insert into transaction values (?,?)";
				PreparedStatement statement2T = con.prepareStatement(sql2T);
				statement2T.setString(1, userIdReceiver);
				statement2T.setString(2, amount + " Received From " + userIdSender);
				statement2T.execute();
				result = true;
			} else {
				String sql3="update wallet set balance=balance+"+amount+"where userid="+userIdSender+"'";
				PreparedStatement stmt3 = con.prepareStatement(sql3);
				stmt3.execute();
				throw new WalletException(IWalletException.ERROR6);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
				
				
				
				

	public ArrayList<String> printTransactions(String userId) {

		Connection con = DBUtil.getConnection();
		String sql = "Select transactioninfo from Transaction where userid='" + userId + "'";
		ArrayList<String> list = new ArrayList<String>();
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet set = statement.executeQuery();

			while (set.next()) {
				list.add(set.getString("transactioninfo"));
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public String login(String id, String password) throws WalletException {
		Connection con = DBUtil.getConnection();
		String userId = null;
		String pass = null;
		String sql = "SELECT * FROM wallet WHERE userid like '" + id + "' AND password like '" + password + "'";

		try {
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet set = statement.executeQuery(sql);
			while (set.next()) {
				pass = set.getString("password");
			}
			userId = id;
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (pass.equals(password)) {
			return userId;
		} else
			return null;
	}
}