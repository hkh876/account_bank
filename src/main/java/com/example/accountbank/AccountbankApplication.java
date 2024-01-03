package com.example.accountbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AccountbankApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountbankApplication.class, args);
    }

}
