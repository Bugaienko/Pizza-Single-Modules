package balu.pizza.webapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AutoConfiguration
public class PizzaSiteAppApplication {
	public static void main(String[] args) {

		SpringApplication.run(PizzaSiteAppApplication.class, args);
//		SpringApplication.run(PizzaSiteAppApplication.class, "--spring.config.name=application-client");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
