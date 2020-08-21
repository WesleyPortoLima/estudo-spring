package com.wesleylima.estudo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractMailService{

	@Autowired MailSender mailSender;

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		mailSender.send(msg);
		
	}
	
}
