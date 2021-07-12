package com.gnsofttr.metronicmongodb.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Async
	public void sendEmail(SimpleMailMessage token) {
		mailSender.send(token);
	}

	public void mailSenderFunction(String email, String verifykey) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("maili gönderen e posta adresi");
		msg.setTo(email);
		msg.setSubject("Activate your account.");
		msg.setText("Thank you for registering. Please click on the below link to activate your account.\n\n"
				+ "http://localhost:8080/registration/verify" + "/reg/" + verifykey);
		mailSender.send(msg);
	}

	
	
	
	public void sendOtpMessage(String to, String subject, String message) throws MessagingException {
	
		 MimeMessage msg = mailSender.createMimeMessage();

	        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(message, true);
	        mailSender.send(msg);
   }
}
