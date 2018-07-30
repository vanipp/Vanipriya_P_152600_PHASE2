package com.cg.paymentwallet.dto;

public class Customer {
	private String name;
	private String phNumber;
	private String emailId;

	public Customer() {
		}


	public Customer(String name, String phNumber, String emailId) {
	super();
	this.name = name;
	this.phNumber = phNumber;
	this.emailId = emailId;
}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhNumber() {
		return phNumber;
	}

	public void setPhNumber(String phNumber) {
		this.phNumber = phNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
