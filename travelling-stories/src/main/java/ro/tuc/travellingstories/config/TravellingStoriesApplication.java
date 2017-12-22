package ro.tuc.travellingstories.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = "ro.tuc.travellingstories")
@EnableJpaRepositories(basePackages = "ro.tuc.travellingstories.repositories")
@EnableTransactionManagement
@EntityScan(basePackages = "ro.tuc.travellingstories.entities")
public class TravellingStoriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravellingStoriesApplication.class, args);
	}
}
