package ru.devag.kamc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;*/

@SpringBootApplication
/*@EnableAsync
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"ru.devag.kamc.repo"})
@EntityScan(basePackages = "ru.devag.kamc.model")*/
public class KamcApplication {
	public static void main(String[] args) {
		SpringApplication.run(KamcApplication.class, args);
	}
}
