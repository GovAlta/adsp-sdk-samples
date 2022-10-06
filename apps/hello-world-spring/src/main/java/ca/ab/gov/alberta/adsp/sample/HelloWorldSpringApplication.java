package ca.ab.gov.alberta.adsp.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
public class HelloWorldSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldSpringApplication.class, args);
	}
}
