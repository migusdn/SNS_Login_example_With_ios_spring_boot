package com.migusdn.snslogindemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SnsLoginDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnsLoginDemoApplication.class, args);
	}

}
