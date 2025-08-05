package com.app.nouapp.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String name,String email) {
		String subject=" Welcome to NOU e-Gyan portal ";
		String message=" Hello Dear, "+name+"\nYour registration is succesful on NOU e-Gyan Portal.\nNow you can login through your credentials.\n\nThank You😊\nAdmin Adarsh Pal";
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject(subject);
		msg.setText(message);
		
		mailSender.send(msg);
		
	}
}
