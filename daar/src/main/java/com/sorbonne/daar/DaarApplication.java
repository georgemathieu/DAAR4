package com.sorbonne.daar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.sorbonne.daar.controller.DAARController;

@SpringBootApplication
@ComponentScan(basePackageClasses = DAARController.class)
public class DaarApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaarApplication.class, args);
	}

}
