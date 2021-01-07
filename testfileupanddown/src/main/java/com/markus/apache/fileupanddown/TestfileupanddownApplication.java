package com.markus.apache.fileupanddown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class TestfileupanddownApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestfileupanddownApplication.class, args);
	}

}
