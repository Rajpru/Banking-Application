package com.project.bank.dto;

import java.util.List;

import com.project.bank.entity.Address;



public class CustomerDisplay {
	private int customerId;
	private String customerName;
	private String mobielNo;
	private String email;
	private String password;
	List<Address> address;
	
	public CustomerDisplay() {
		super();
	}
	
	

	public CustomerDisplay(int customerId, String customerName, String mobielNo, String email, List<Address> address) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.mobielNo = mobielNo;
		this.email = email;
		this.address = address;
	}



	public int getCustomerId() {
		return customerId;
	}



	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}



	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public String getMobielNo() {
		return mobielNo;
	}



	public void setMobielNo(String mobielNo) {
		this.mobielNo = mobielNo;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public List<Address> getAddress() {
		return address;
	}



	public void setAddress(List<Address> address) {
		this.address = address;
	}

	


	
}
