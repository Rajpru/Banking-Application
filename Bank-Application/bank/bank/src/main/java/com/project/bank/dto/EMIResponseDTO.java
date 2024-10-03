package com.project.bank.dto;

public class EMIResponseDTO {
    private int loanId;
    private double emi;
    private String loanStatus;
    private String message;
    private double principalAmount;
    private int accNo;

	public EMIResponseDTO(int loanId, double emi, String loanStatus, String message,double principalAmount,int accNo) {
		super();
		this.loanId = loanId;
		this.emi = emi;
		this.loanStatus = loanStatus;
		this.message = message;
		this.principalAmount=principalAmount;
		this.accNo=accNo;
	}

	public EMIResponseDTO() {
		super();
	}
	
    public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public double getEmi() {
		return emi;
	}

	public void setEmi(double emi) {
		this.emi = emi;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(double principalAmount) {
		this.principalAmount = principalAmount;
	}

	public int getAccNo() {
		return accNo;
	}

	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}




    
}
