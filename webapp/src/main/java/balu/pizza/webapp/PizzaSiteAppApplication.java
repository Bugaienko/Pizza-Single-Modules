package balu.pizza.webapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main application launch class
 * <p>
 * Pizza chain website with Rest API.
 * Back-end of the site, web representation and implemented REST API.
 * Part of the site is available to all users.
 * More functionality is available only to registered and authorized users.
 * Site administrator can modify and add all entities.
 * @author Sergii Bugaienko "sergiibygaienko@gmail.com"
 * @version 1.0
 *
 */

@SpringBootApplication
@AutoConfiguration
public class PizzaSiteAppApplication {
	/**
	 * Here start point of the app
	 * @param args command line values
	 */
	public static void main(String[] args) {

		SpringApplication.run(PizzaSiteAppApplication.class, args);
//		SpringApplication.run(PizzaSiteAppApplication.class, "--spring.config.name=application-client");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
