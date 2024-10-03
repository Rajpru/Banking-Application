package com.project.bank.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bank.dto.AccountDisplay;
import com.project.bank.entity.Account;
import com.project.bank.repository.AccountRepository;
import com.project.bank.repository.CustomerRepository;

@Service
public class AccountService {

	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private EmailService emailService;
	
	public Account registerAccount(Account account) {
		return accountRepository.save(account);
	}
	public Account findAccountByAccountNo(int accNo) {
		Optional<Account> accountOptional = accountRepository.findById(accNo);
		if(accountOptional.isPresent()) {
			return accountOptional.get();
		}
		return null;
	}
	
	public List<AccountDisplay> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		return accounts.stream()
			.map(account -> new AccountDisplay(
				account.getAccNo(),
				account.getAccType(),
				account.getDate(),
				account.getBalance(),
				account.getStatus()
			))
			.collect(Collectors.toList());
	}
	
	public boolean activateOrDeactivateAccount(int accNo,boolean activate) {
		Optional<Account> account = accountRepository.findById(accNo);
		
		if(account!=null) {
			Account acc=account.get();
			acc.setStatus(activate ? "ACTIVE":"INACTIVE");
			accountRepository.save(acc);
			return true;
		}
		return false;
	}
	
	public void updateAccountBalance(int accNo, double newBalance) {
		Account account = accountRepository.findById(accNo).
				orElseThrow(()-> new RuntimeException("Account not found with id: "+accNo));
		
		account.setBalance(newBalance);
		accountRepository.save(account);
		
		if(newBalance==0) {
			emailService.sendAccountBalanceEmail("harishmunagala42@gmail.com", account.getAccNo(), newBalance);
		}
		
	}
	
	
}
