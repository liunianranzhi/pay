package com.yijia360.pay.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  
public class PayAdminApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(PayAdminApplication.class, args);
		
	}
	
}