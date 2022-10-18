package ca.ab.gov.alberta.adsp.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;

@SpringBootApplication
@EnableReactiveMethodSecurity
public class HelloWorldSpringFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldSpringFluxApplication.class, args);
	}

}
