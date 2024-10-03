package com.project.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {
	
	
	@Autowired
	private JavaMailSender emailSender;
	
    public void sendAccountBalanceEmail(String to, int accountNo, double balance) {
    	SimpleMailMessage message=new SimpleMailMessage();
    	message.setTo(to);
    	message.setSubject("Account Balance Alert");
    	message.setText("Account Number: "+ accountNo+ "\n Balance: "+balance);
    	emailSender.send(message);
    }


}
