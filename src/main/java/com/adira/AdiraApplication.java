package com.adira;

import com.adira.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class AdiraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AdiraApplication.class, args);
	}
	@Autowired
	EmailService emailService;

	@Override
	public void run(String... strings) throws Exception {
		emailService.sendEmail("didiyudha@gmail.com", "kioson.xero.integration@gmail.com");
	}
}
