package com.ureca.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class SpringPersonMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPersonMvcApplication.class, args);
	}

}
