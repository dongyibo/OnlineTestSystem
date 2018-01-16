package edu.nju.qstlib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("edu.nju.qstlib")
@EnableEurekaClient
public class QstlibApplication {

	public static void main(String[] args) {
		SpringApplication.run(QstlibApplication.class, args);
	}
}
