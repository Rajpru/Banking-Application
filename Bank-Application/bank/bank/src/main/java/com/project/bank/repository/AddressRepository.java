package com.project.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bank.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer>{

}
