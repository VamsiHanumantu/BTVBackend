package com.btv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BtvNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtvNewsApplication.class, args);
	}
     
}
