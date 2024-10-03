package com.project.bank.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.dto.CustomerDisplay;
import com.project.bank.entity.Account;
import com.project.bank.entity.Address;
import com.project.bank.entity.Customer;
import com.project.bank.service.AccountService;
import com.project.bank.service.AddressService;
import com.project.bank.service.CustomerService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("api/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AccountService accountService;
	
	

	
	@PostMapping(value="/register",consumes = "application/json")
	public ResponseEntity<String> registerCustomer(@RequestBody Customer customer)
	{
		if(customer.getAddress()==null) {
			return  ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Address information required");
		}

		if(customer.getAccount()==null) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Account details required");
		}
		Customer savedCutomer = customerService.registerCustomer(customer);
		List<Address> address = customer.getAddress();
		for(Address address1: address) {
			address1.setCustomer(savedCutomer);
			addressService.registerAddress(address1);
		}
		savedCutomer.setAddress(address);

		List<Account> account=customer.getAccount();
		for(Account acc:account) {
			acc.setCustomer(savedCutomer);
			acc.setBalance(0.0);
			acc.setDate(new Date());
			acc.setStatus("active");
			accountService.registerAccount(acc);
		}
		savedCutomer.setAccount(account);
		
		Customer registerCustomer=customerService.registerCustomer(customer);
		if(registerCustomer!=null) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Customer opened Successfully");
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer Account failed to open");
		}
		
	}
	
	@PostMapping("/login")
	public String loginCustomer(@RequestBody Map<String,String>loginData){
		String emailId=loginData.get("email");
		String password=loginData.get("password");
		Customer loginCustomer=customerService.loginCustomer(emailId,password);
			
	if(loginCustomer!=null) {
		  return "Success";
    } else {
        return "Login failed";
    }
	}
	
	
		
	@GetMapping("/allcustomers")
	public ResponseEntity<List<CustomerDisplay>> getAllCustomers(){
	    List<CustomerDisplay> customers = customerService.getAllCustomers();
	    return ResponseEntity.ok(customers);
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
}