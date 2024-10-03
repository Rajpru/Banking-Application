package com.project.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bank.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan,Integer>{

}
