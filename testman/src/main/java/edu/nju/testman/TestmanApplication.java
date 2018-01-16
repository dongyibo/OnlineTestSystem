package edu.nju.testman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TestmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestmanApplication.class, args);
	}
}
