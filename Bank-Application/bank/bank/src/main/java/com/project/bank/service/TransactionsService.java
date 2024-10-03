package com.project.bank.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bank.entity.Account;
import com.project.bank.entity.Transactions;
import com.project.bank.repository.AccountRepository;
import com.project.bank.repository.TransactionsRepository;

@Service
public class TransactionsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Transactional
    public String transferFunds(int sourceAccNo, int destAccNo, double amount) {
    	try {
	        Account sourceAccount = accountRepository.findById(sourceAccNo)
	                .orElseThrow(()-> new IllegalArgumentException("Source account not found"));
	        
	        if(sourceAccount == null) {
	        	return "Source account not found";
	        }
	        Account destAccount = accountRepository.findById(destAccNo)
	                .orElseThrow(()-> new IllegalArgumentException("Destination account not found"));
	       
	        if(destAccount == null) {
	        	return "Destination account not found";
	        }
	       
	        if (sourceAccount.getBalance() < amount) {
	            return "Insufficient funds in source account";
	        }
	
	       
	        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
	        destAccount.setBalance(destAccount.getBalance() + amount);
	
	        Transactions sourceTransaction = new Transactions(
	                new Date(), "DEBIT", amount, "COMPLETED", sourceAccount);
	        transactionsRepository.save(sourceTransaction);
	
	        
	        Transactions destTransaction = new Transactions(
	                new Date(), "CREDIT", amount, "COMPLETED", destAccount);
	        transactionsRepository.save(destTransaction);
	
	      
	        accountRepository.save(sourceAccount);
	        accountRepository.save(destAccount);
	
	        return "Transfer successful";
    	}catch(Exception ex){
    		return "Transaction failed:"+ex.getMessage();
    		
    	}
    }
    
    public List<Transactions> getTransactionsByAccNo(int accNo) {
        Account account = accountRepository.findById(accNo)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return transactionsRepository.findByAccount(account);
    }
}
