package com.project.learningz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LearningzApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningzApplication.class, args);
	}

}
