package com.dyks.aihubbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AIHubBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AIHubBeApplication.class, args);
	}

}
