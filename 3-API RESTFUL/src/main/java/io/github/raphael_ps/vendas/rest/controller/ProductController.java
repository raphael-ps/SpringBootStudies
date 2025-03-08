package io.github.raphael_ps.vendas.rest.controller;

import io.github.raphael_ps.vendas.domain.entity.Product;
import io.github.raphael_ps.vendas.domain.repository.ProductRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/product")

public class ProductController {

    private final ProductRepository prodRepo;

    public ProductController(ProductRepository prodRepo) {
        this.prodRepo = prodRepo;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product registerProduct(@RequestBody Product newProd){
        return prodRepo.save(newProd);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Integer id){
        Optional<Product> optionalProduct = prodRepo.findById(id);

        return optionalProduct.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable Integer id, @RequestBody Product product){
        prodRepo.findById(id).ifPresentOrElse(
                (result) -> {
                    product.setId(result.getId());
                    prodRepo.save(product);
                },
                () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        prodRepo
                .findById(id)
                .ifPresentOrElse(prodRepo::delete,
                        ()-> {throw new ResponseStatusException(HttpStatus.NOT_FOUND); }
                );
    }

    @GetMapping("/filter")
    public List<Product> filterProducts(Product filter){
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING);

        Example<Product> productExample = Example.of(filter, exampleMatcher);

        return prodRepo.findAll(productExample);
    }
}
