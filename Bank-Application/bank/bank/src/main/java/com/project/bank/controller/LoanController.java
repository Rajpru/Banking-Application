package com.project.bank.controller;

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

import com.project.bank.dto.EMIResponseDTO;
import com.project.bank.dto.LoanDTO;
import com.project.bank.service.LoanService;

import jakarta.persistence.EntityNotFoundException;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("api/loans")
public class LoanController {
	
	
	@Autowired
	private LoanService loanService;
	
	
	 @PostMapping("/apply")
	    public ResponseEntity<String> applyLoan(@RequestBody LoanDTO loanDTO) {
	        try {
	            loanService.applyLoan(loanDTO);
	            return ResponseEntity.status(HttpStatus.CREATED).body("Loan application submitted successfully");
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        }
	    }
	    
	    @PutMapping("/{loanId}/status")
	    public ResponseEntity<LoanDTO> approveOrRejectLoan(
	            @PathVariable int loanId, 
	            @RequestParam("status") String status) {
	        LoanDTO updatedLoan = loanService.approveOrRejectLoan(loanId, status);
	        return ResponseEntity.ok(updatedLoan);
	    }
	    
	    @GetMapping("/emi/{accNo}")
	    public ResponseEntity<?> getEMIForLoansByAccount(@PathVariable("accNo") int accNo) {
	        try {
	            List<EMIResponseDTO> emiList = loanService.calculateEMIForLoansByAccount(accNo);
	            if (emiList.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No loans applied for this account");
	            }
	            return ResponseEntity.ok(emiList);
	        } catch (EntityNotFoundException ex) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	        } catch (RuntimeException ex) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	        }
	    }

}
