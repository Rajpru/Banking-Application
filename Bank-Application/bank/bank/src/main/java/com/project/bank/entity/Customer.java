package com.project.bank.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Cusotmer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	private String customerName;
	private String mobielNo;
	private String email;
	private String password;
	
	@OneToMany(mappedBy="customer")
	List<Address> address;
	
	@OneToMany(mappedBy="customer")
	List<Account> account;

	public Customer() {
		super();
	}

	public Customer(int customerId,String customerName, String mobielNo, String email, String password, List<Address> address,
			List<Account> account) {
		super();
		this.customerId=customerId;
		this.customerName = customerName;
		this.mobielNo = mobielNo;
		this.email = email;
		this.password = password;
		this.address = address;
		this.account = account;
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

	public List<Account> getAccount() {
		return account;
	}

	public void setAccount(List<Account> account) {
		this.account = account;
	}
	
	

}
