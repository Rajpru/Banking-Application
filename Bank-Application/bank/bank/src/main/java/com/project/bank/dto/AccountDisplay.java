package com.project.bank.dto;

import java.util.Date;

public class AccountDisplay {
	private int accNo;
	private String accType;
	private Date date;
	private double balance;
	private String status;
	public AccountDisplay() {
		super();
	}
	public AccountDisplay(int accNo, String accType, Date date, double balance, String status) {
		super();
		this.accNo = accNo;
		this.accType = accType;
		this.date = date;
		this.balance = balance;
		this.status = status;
	}
	public int getAccNo() {
		return accNo;
	}
	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
