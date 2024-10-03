package com.project.bank.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bank.entity.Account;
import com.project.bank.entity.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
	List<Transactions> findByAccount(Account account);
}

