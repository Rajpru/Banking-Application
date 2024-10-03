package com.project.Service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bank.dto.AccountDisplay;
import com.project.bank.dto.CustomerDisplay;
import com.project.bank.entity.Account;
import com.project.bank.entity.Customer;
import com.project.bank.repository.AccountRepository;
import com.project.bank.repository.CustomerRepository;
import com.project.entity.Admin;
import com.project.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
   private AccountRepository accountRepository;
    
    
    
    public String registerAdmin(Admin admin) {
        if (adminRepository.findByEmail(admin.getEmail()) != null) {
            return "Email already registered";
        }

        adminRepository.save(admin);
        return "Admin registered successfully";
    }
    
    

    public Admin loginAdmin(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null && password.equals(admin.getPassword())) {
            return admin;
        }
        return null;
    }
    
    public Customer getCustomerByAccountNo(int accNo) {
        return customerRepository.findCustomerByAccountNo(accNo);
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
	
    public List<CustomerDisplay> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
            .map(customer -> new CustomerDisplay(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getMobielNo(),
                customer.getEmail(),
                customer.getAddress()
            ))
            .collect(Collectors.toList());
    }
}

