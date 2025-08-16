package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ApplicantModule {

	public static void main(String[] args) {
		SpringApplication.run(ApplicantModule.class, args);
		System.out.println("Application Started");
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
