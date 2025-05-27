package com.example.proiect1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Proiect1Application {

    public static void main(String[] args) {
        SpringApplication.run(Proiect1Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {

        return args -> {

        };
    }



}