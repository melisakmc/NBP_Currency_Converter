package com.example.NBPService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"controller"})
public class NbpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NbpServiceApplication.class, args);
		
	}

}
