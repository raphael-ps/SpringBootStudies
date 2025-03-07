package io.github.raphael_ps.vendas;

import io.github.raphael_ps.vendas.domain.entity.Customer;
import io.github.raphael_ps.vendas.domain.entity.OrderDetails;
import io.github.raphael_ps.vendas.domain.entity.Product;
import io.github.raphael_ps.vendas.domain.entity.SalesOrder;
import io.github.raphael_ps.vendas.domain.repository.CustomerRepository;
import io.github.raphael_ps.vendas.domain.repository.OrderDetailsRepository;
import io.github.raphael_ps.vendas.domain.repository.ProductRepository;
import io.github.raphael_ps.vendas.domain.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@SpringBootApplication

public class VendasApplication {

	@Bean
	public CommandLineRunner run(@Autowired CustomerRepository customerRepository,
								 @Autowired ProductRepository productRepository,
								 @Autowired SalesOrderRepository salesOrderRepository,
								 @Autowired OrderDetailsRepository orderDetailsRepository
	){
		return args -> {
			Customer newCustomer = new Customer("Raphael Pinheiro");
			customerRepository.save(newCustomer);

			SalesOrder newOrder = new SalesOrder();
			newOrder.setCustomer(newCustomer);
			newOrder.setCreatedAt(LocalDate.now());
			newOrder.setTotalAmount(BigDecimal.valueOf(0));
			salesOrderRepository.save(newOrder);

			Product socks = new Product("Meias", BigDecimal.valueOf(25.90));
			Product tShirt = new Product("Camisa", BigDecimal.valueOf(69.90));
			productRepository.save(socks);
			productRepository.save(tShirt);

			OrderDetails item = new OrderDetails();
			item.setProduct(socks);
			item.setOrder(newOrder);
			item.setQuantity(3);
			orderDetailsRepository.save(item);

			OrderDetails item2 = new OrderDetails();
			item2.setQuantity(2);
			item2.setOrder(newOrder);
			item2.setProduct(tShirt);
			orderDetailsRepository.save(item2);

			SalesOrder fetchedOrder = salesOrderRepository.findOrderFetchItems(newOrder.getId());

			System.out.println(fetchedOrder);

		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
