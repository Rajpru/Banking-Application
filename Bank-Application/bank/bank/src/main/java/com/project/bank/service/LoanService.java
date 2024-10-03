package com.project.bank.service;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bank.dto.EMIResponseDTO;
import com.project.bank.dto.LoanDTO;
import com.project.bank.entity.Account;
import com.project.bank.entity.Loan;
import com.project.bank.repository.AccountRepository;
import com.project.bank.repository.LoanRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LoanService {
	
	 	@Autowired
	    private LoanRepository loanRepository;

	    @Autowired
	    private AccountRepository accountRepository;
	    

	    public void applyLoan(LoanDTO loanDTO) {
	        Loan loan = new Loan();
	        loan.setLoanType(loanDTO.getLoanType());
	        loan.setAmount(loanDTO.getAmount());
	        loan.setTenure(loanDTO.getTenure());
	        loan.setAppliedDate(new Date());
	        loan.setLoanStatus("Pending");

	        switch (loanDTO.getLoanType()) {
	            case "Home Loan":
	                loan.setRateOfInterest(10.0);
	                break;
	            case "Personal Loan":
	                loan.setRateOfInterest(13.0);
	                break;
	            case "Educational Loan":
	                loan.setRateOfInterest(8.0);
	                break;
	            default:
	                throw new IllegalArgumentException("Invalid loan type");
	        }

	        Optional<Account> account = accountRepository.findById(loanDTO.getAccountAccNo());
	        if (account.isPresent()) {
	            loan.setAccount(account.get());
	        } else {
	            throw new EntityNotFoundException("Account not found");
	        }

	        loanRepository.save(loan);
	    }
	    
	    public LoanDTO approveOrRejectLoan(int loanId, String status) {
	        
	        Loan loan = loanRepository.findById(loanId)
	                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));

	       
	        if (!status.equalsIgnoreCase("Approved") && !status.equalsIgnoreCase("Rejected")) {
	            throw new IllegalArgumentException("Invalid status. Status should be either 'Approved' or 'Rejected'.");
	        }

	        loan.setLoanStatus(status);
	        Loan updatedLoan = loanRepository.save(loan);

	     
	        LoanDTO updatedLoanDTO = new LoanDTO();
	        updatedLoanDTO.setLoanId(updatedLoan.getLoanId());
	        updatedLoanDTO.setLoanType(updatedLoan.getLoanType());
	        updatedLoanDTO.setAmount(updatedLoan.getAmount());
	        updatedLoanDTO.setTenure(updatedLoan.getTenure());
	        updatedLoanDTO.setAppliedDate(updatedLoan.getAppliedDate());
	        updatedLoanDTO.setRateOfInterest(updatedLoan.getRateOfInterest());
	        updatedLoanDTO.setLoanStatus(updatedLoan.getLoanStatus());
	        updatedLoanDTO.setAccountAccNo(updatedLoan.getAccount().getAccNo());

	        return updatedLoanDTO;
	    }
	    
	    public List<EMIResponseDTO> calculateEMIForLoansByAccount(int accNo) {
	        Optional<Account> accountOptional = accountRepository.findById(accNo);

	        if (!accountOptional.isPresent()) {
	            throw new EntityNotFoundException("Account details not found");
	        }

	        Account account = accountOptional.get();
	        
	        List<Loan> loans = account.getLoans();
	        

	        if (loans.isEmpty()) {
	            throw new RuntimeException("No loans applied for this account");
	        }

	        List<EMIResponseDTO> emiResponseList = new ArrayList<>();
	        for (Loan loan : loans) {
	            EMIResponseDTO emiResponseDTO = new EMIResponseDTO();
	            emiResponseDTO.setAccNo(account.getAccNo());
	            emiResponseDTO.setLoanId(loan.getLoanId());
	            emiResponseDTO.setPrincipalAmount(loan.getAmount());

	           
	            if (loan.getLoanStatus().equalsIgnoreCase("Pending")) {
	                emiResponseDTO.setMessage("Loan status is still pending for loan ID: " + loan.getLoanId());
	            } else if (loan.getLoanStatus().equalsIgnoreCase("Rejected")) {
	                emiResponseDTO.setMessage("Applied loan rejected for loan ID: " + loan.getLoanId());
	            } else {
	                
	                double emi = calculateEMI(loan.getAmount(), loan.getRateOfInterest(), loan.getTenure());
	                emiResponseDTO.setEmi(emi);
	                emiResponseDTO.setMessage("EMI calculated successfully");
	            }

	            emiResponseDTO.setLoanStatus(loan.getLoanStatus());
	            emiResponseList.add(emiResponseDTO);
	        }

	        return emiResponseList;
	    }

	    private double calculateEMI(double principal, double annualRate, int tenureYears) {
	        double monthlyRate = annualRate / (12 * 100); 
	        int tenureMonths = tenureYears; 

	        return (principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
	                (Math.pow(1 + monthlyRate, tenureMonths) - 1);
	    }
	}