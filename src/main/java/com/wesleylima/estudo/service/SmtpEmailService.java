package com.wesleylima.estudo.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractMailService{

	@Autowired MailSender mailSender;
	
	@Autowired JavaMailSender javaMailSender;

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		mailSender.send(msg);
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		javaMailSender.send(msg);
	}
	
}
