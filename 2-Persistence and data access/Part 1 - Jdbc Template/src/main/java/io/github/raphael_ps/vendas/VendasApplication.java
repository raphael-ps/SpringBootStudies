package io.github.raphael_ps.vendas;

import io.github.raphael_ps.vendas.domain.entity.Customer;
import io.github.raphael_ps.vendas.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication

public class VendasApplication {

	@Bean
	public CommandLineRunner init(@Autowired CustomerRepository customerRepository){
		return args -> {
			customerRepository.save(new Customer("Raphael Pinheiro"));
			customerRepository.save(new Customer("**** *******"));
			customerRepository.save(new Customer("Ivfei Carvalho"));
			customerRepository.save(new Customer("Roberto Pinheiro"));

			customerRepository.updateCustomer(new Customer(2, "José Pinheiro"));

			List<Customer> customers = customerRepository.getAllByName("Pinheiro");
			customers.forEach(c -> {
				c.setName(c.getName() + " Check");
				customerRepository.updateCustomer(c);
			});

			customers = customerRepository.getAll();
			customers.forEach(System.out::println);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
