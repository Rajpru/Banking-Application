package com.project.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.bank.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

	Customer findByEmail(String email);
	
    @Query("SELECT c FROM Customer c JOIN c.account a WHERE a.accNo = :accNo")
    Customer findCustomerByAccountNo(@Param("accNo") int accNo);

}
