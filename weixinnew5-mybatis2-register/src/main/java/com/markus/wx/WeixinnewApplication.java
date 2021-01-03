package com.markus.wx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.markus.wx.mapper")
public class WeixinnewApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeixinnewApplication.class, args);
	}

}
