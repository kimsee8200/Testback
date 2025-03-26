package org.example.plain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class, scanBasePackages = "org.example.plain")
public class PlainApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlainApplication.class, args);
	}

}
