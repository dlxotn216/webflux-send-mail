package demo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class WebfluxMailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxMailSenderApplication.class, args);
	}

}
