package com.wesleylima.estudo.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.wesleylima.estudo.service.DBService;
import com.wesleylima.estudo.service.EmailService;
import com.wesleylima.estudo.service.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
