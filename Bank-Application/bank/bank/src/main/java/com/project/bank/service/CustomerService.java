package com.project.bank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bank.dto.CustomerDisplay;
import com.project.bank.entity.Customer;
import com.project.bank.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	
	public Customer registerCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	
    public Customer getCustomerDetailsByAccNo(int accNo) {
        return customerRepository.findCustomerByAccountNo(accNo);
    }
	
	public Customer loginCustomer(String email,String password) {
		Customer customer=customerRepository.findByEmail(email);
		if(customer!=null && password.equals(customer.getPassword()))
				{
			return customer;
		}else
		{
			return null;
		}
	}
	
    public Customer getCustomerByAccountNo(int accNo) {
        return customerRepository.findCustomerByAccountNo(accNo);
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
