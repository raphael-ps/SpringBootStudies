package io.github.raphael_ps.vendas.rest.controller;

import ch.qos.logback.core.net.server.Client;
import io.github.raphael_ps.vendas.domain.entity.Customer;
import io.github.raphael_ps.vendas.domain.repository.CustomerRepository;
import org.apache.coyote.Response;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer registerCustomer(@RequestBody Customer newCustomer){
        customerRepository.save(newCustomer);
        return newCustomer;
    }

    @GetMapping("/find/{id}")
    public Customer findById(@PathVariable(name = "id") Integer id){
        Optional<Customer> result = customerRepository.findById(id);

        return result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable(name = "id") Integer id){
        Optional<Customer> result = customerRepository.findById(id);

        result.ifPresentOrElse(customerRepository::delete,
                () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND); });

    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable("id") Integer customerId, @RequestBody Customer customerUpdated){
        Optional<Customer> result = customerRepository.findById(customerId);

        result.ifPresentOrElse((customerFound) -> {
            customerUpdated.setId(customerFound.getId());
            customerRepository.save(customerUpdated);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

    }

    @GetMapping("/filter")
    public List<Customer> filterCustomers(Customer filter){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Example<Customer> example = Example.of(filter, matcher);
        return customerRepository.findAll(example);
    }
}