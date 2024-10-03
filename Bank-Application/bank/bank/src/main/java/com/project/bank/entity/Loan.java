package com.project.bank.entity;



import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loanId;
    public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	private String loanType;
    private double amount;
    private int tenure; 
    private Date appliedDate;
    private double rateOfInterest;
    private String loanStatus;

    @ManyToOne
    @JoinColumn(name = "accNo", referencedColumnName = "accNo")
    @JsonBackReference
    private Account account;

    
    public Loan() {
        super();
    }

    public Loan(String loanType, double amount, int tenure, Date appliedDate, double rateOfInterest, String loanStatus) {
        super();
        this.loanType = loanType;
        this.amount = amount;
        this.tenure = tenure;
        this.appliedDate = appliedDate;
        this.rateOfInterest = rateOfInterest;
        this.loanStatus=loanStatus;
        
    }
    public String loanStatus() {
    	return loanStatus;
    }
    public void loanStatus(String loanStatus) {
    	this.loanStatus=loanStatus;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTenure() {
        return tenure;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }

    public Date getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }

    public double getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(double rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

