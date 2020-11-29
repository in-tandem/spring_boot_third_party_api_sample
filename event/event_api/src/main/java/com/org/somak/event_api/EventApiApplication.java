package com.org.somak.event_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventApiApplication {

	private static final String SECRET_KEY = "b2fc6f85ae1e4a6fbd779ffe67d186fc";
	private static final String JASYPT_ENCRIPTOR = "jasypt.encryptor.password";
	
	static {
		System.setProperty(JASYPT_ENCRIPTOR, SECRET_KEY);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EventApiApplication.class, args);
	}

}
