package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.services.UserInteractionService;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private UserInteractionService userInteractionService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		userInteractionService.handleUserInteraction();
	}
}
