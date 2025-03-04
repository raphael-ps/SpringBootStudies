package io.github.raphael_ps.vendas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Configuration
@Profile("development")
public class CustomConfiguration {

    @Bean
    @Order(1)
    public CommandLineRunner helloDevProfile(){
        return args -> {
            System.out.println("RODANDO EM AMBIENTE DE DESENVOLVIMENTO!");
        };
    }
}
