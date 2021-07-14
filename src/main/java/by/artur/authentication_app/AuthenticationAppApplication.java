package by.artur.authentication_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuthenticationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationAppApplication.class, args);
	}

}
