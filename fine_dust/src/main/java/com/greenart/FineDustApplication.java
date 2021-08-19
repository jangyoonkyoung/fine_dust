package com.greenart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FineDustApplication {

	public static void main(String[] args) {
		SpringApplication.run(FineDustApplication.class, args);
	}

}
