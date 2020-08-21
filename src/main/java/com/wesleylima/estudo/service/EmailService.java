package com.wesleylima.estudo.service;

import org.springframework.mail.SimpleMailMessage;

import com.wesleylima.estudo.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
