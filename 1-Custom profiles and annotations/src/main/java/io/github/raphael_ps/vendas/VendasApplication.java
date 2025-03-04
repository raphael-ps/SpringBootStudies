package io.github.raphael_ps.vendas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApplication {
	@Value( "${application.name:notFound}" )
	private String applicationName;

	@Cat
	private Animal animal;

	@Bean
	public CommandLineRunner run(){
		return args -> {
			this.animal.makeNoise();
		};
	}
	@GetMapping("/hello")
	public String helloApplication(){

		return applicationName;
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
