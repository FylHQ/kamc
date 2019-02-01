package ru.devag.kamc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KamcApplication {
	public static void main(String[] args) {
		SpringApplication.run(KamcApplication.class, args);
	}
}

