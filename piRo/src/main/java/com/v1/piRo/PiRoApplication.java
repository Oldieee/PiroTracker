package com.v1.piRo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class PiRoApplication extends Object {

	public static void main(String[] args) {
		SpringApplication.run(PiRoApplication.class, args);
	}

}
