package com.company.deliveries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DeliverySchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliverySchedulerApplication.class, args);
	}

}
