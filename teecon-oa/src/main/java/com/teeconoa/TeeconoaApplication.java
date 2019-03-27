package com.teeconoa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan("com.teeconoa.project.*.*.mapper")
public class TeeconoaApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(TeeconoaApplication.class, args);
	}

}
	