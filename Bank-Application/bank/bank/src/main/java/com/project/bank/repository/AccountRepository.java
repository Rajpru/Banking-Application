package com.project.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bank.entity.Account;
import com.project.bank.entity.Customer;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

	

}
