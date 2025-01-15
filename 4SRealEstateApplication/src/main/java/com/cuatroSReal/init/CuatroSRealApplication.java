package com.cuatroSReal.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cuatroSReal")
public class CuatroSRealApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuatroSRealApplication.class, args);
	}

}
