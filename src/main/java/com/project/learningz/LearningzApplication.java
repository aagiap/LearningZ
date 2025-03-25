package com.project.learningz;

import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.shared.utils.LogUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LearningzApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(LearningzApplication.class, args);
		LogUtils.init();
	}


}
