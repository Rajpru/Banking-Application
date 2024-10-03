package com.project.bank.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.dto.AccountDisplay;
import com.project.bank.entity.Account;
import com.project.bank.entity.Transactions;
import com.project.bank.repository.TransactionsRepository;
import com.project.bank.service.AccountService;
import com.project.bank.service.TransactionsService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("api/accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
    private TransactionsService transactionsService;
	
	@Autowired
	private TransactionsRepository transactionRepository;
	
	@GetMapping("/account/{accNo}")
	public ResponseEntity<AccountDisplay> getAccountByAccountNo(@PathVariable int accNo) {
	    Account account = accountService.findAccountByAccountNo(accNo);

	    if (account == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }

	    AccountDisplay ad = new AccountDisplay();
	    ad.setAccNo(account.getAccNo());
	    ad.setAccType(account.getAccType());
	    ad.setBalance(account.getBalance());
	    ad.setStatus(account.getStatus());
	    ad.setDate(account.getDate());

	    return ResponseEntity.ok(ad);
	}
	
	
	@GetMapping("/allaccounts")
	public ResponseEntity<List<AccountDisplay>> getAllAccounts(){
		List<AccountDisplay> account = accountService.getAllAccounts();
		return ResponseEntity.ok(account);
	}
	
	
	@PostMapping("/withdraw/{accNo}")
	public ResponseEntity<String> withdraw(@RequestParam double withdraw,@PathVariable int accNo){
		Account account = accountService.findAccountByAccountNo(accNo);
		if(account!=null) {
		if(account.getBalance()>=0 && account.getBalance()>=withdraw) {
		account.setBalance(account.getBalance()-withdraw);
		accountService.registerAccount(account);
        Transactions transaction = new Transactions(new Date(), "WITHDRAW", withdraw, "COMPLETED", account);
        transactionRepository.save(transaction);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Amount Withdraw successfull final Balance:"+account.getBalance());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("WithDraw Failed "+account.getBalance());
		}
		else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Account Not found");
		}
	}
	
	@PostMapping("/deposit/{accNo}")
	public ResponseEntity<String> deposit(@RequestParam double deposit,@PathVariable int accNo){
		Account account = accountService.findAccountByAccountNo(accNo);
		if(account!=null) {
		account.setBalance(account.getBalance()+(double)deposit);
		accountService.registerAccount(account);
        Transactions transaction = new Transactions(new Date(), "DEPOSIT", deposit, "COMPLETED", account);
        transactionRepository.save(transaction);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Amount deposited final Balance:"+account.getBalance());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Not Found");
		}
	

    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(
            @RequestParam int sourceAccNo,
            @RequestParam int destAccNo,
            @RequestParam double amount) {
    	try {
        String result = transactionsService.transferFunds(sourceAccNo, destAccNo, amount);
        
            return new ResponseEntity<>(result,HttpStatus.OK);
        } catch (RuntimeException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/transactions/{accNo}")
    public ResponseEntity<List<Transactions>> getTransactionsByAccNo(@PathVariable("accNo") int accNo) {
        List<Transactions> transactions = transactionsService.getTransactionsByAccNo(accNo);
        return ResponseEntity.ok(transactions);
    }
    
    @PutMapping("/activate-or-deactivate/{accNo}")
    public ResponseEntity<String>activateOrDeactivateAccount(@PathVariable int accNo,@RequestParam boolean activate){
    	boolean result = accountService.activateOrDeactivateAccount(accNo, activate);
    	if(result) {
    		return ResponseEntity.ok(activate?"Account activate" : "Account deactivated");
    	}else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
    	}
    }
    
    @PostMapping("/update-balance/{accNo}")
    public String updateAccountBalance(@PathVariable int accNo, @RequestParam double newBalance) {
        accountService.updateAccountBalance(accNo, newBalance);
        return "Account balance updated successfully.";
    }



}
