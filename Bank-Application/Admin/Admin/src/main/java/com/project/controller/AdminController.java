package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.Service.AdminService;
import com.project.bank.dto.AccountDisplay;
import com.project.bank.dto.CustomerDisplay;
import com.project.bank.dto.LoanDTO;
import com.project.bank.entity.Customer;
import com.project.bank.service.AccountService;
import com.project.bank.service.AddressService;
import com.project.bank.service.CustomerService;
import com.project.bank.service.EmailService;
import com.project.bank.service.LoanService;
import com.project.entity.Admin;


@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AddressService addressService;

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;
    
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private EmailService emailService;
	
	
	 @PostMapping("/register")
	    public ResponseEntity<String> registerAdmin(@RequestBody Admin admin) {
	        String result = adminService.registerAdmin(admin);
	        if (result.equals("Admin registered successfully")) {
	            return ResponseEntity.status(HttpStatus.CREATED).body(result);
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	        }
	    }
	
	
	
    
    @PostMapping("/login")
    public String login(@RequestBody Admin admin) {
        Admin loggedInAdmin = adminService.loginAdmin(admin.getEmail(), admin.getPassword());
        if (loggedInAdmin != null) {
            return "Success"; 
        } else {
            return "Login failed";
        }
    }
    
    @GetMapping("/customer/by-account/{accNo}")
    public ResponseEntity<Customer> getCustomerByAccountNo(@PathVariable("accNo") int accNo) {
        Customer customer = customerService.getCustomerByAccountNo(accNo);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/allaccounts")
    public ResponseEntity<List<AccountDisplay>> getAllAccounts(){
    	List<AccountDisplay> account = accountService.getAllAccounts();
    	return ResponseEntity.ok(account);
    }
	@GetMapping("/allcustomers")
	public ResponseEntity<List<CustomerDisplay>> getAllCustomers(){
	    List<CustomerDisplay> customers = customerService.getAllCustomers();
	    return ResponseEntity.ok(customers);
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
    
    @PutMapping("/{loanId}/status")
    public ResponseEntity<LoanDTO> approveOrRejectLoan(
            @PathVariable int loanId, 
            @RequestParam("status") String status) {
        LoanDTO updatedLoan = loanService.approveOrRejectLoan(loanId, status);
        return ResponseEntity.ok(updatedLoan);
    }

}

