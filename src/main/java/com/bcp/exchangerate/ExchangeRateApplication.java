package com.bcp.exchangerate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableWebFluxSecurity
public class ExchangeRateApplication {


    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateApplication.class, args);
    }

}
