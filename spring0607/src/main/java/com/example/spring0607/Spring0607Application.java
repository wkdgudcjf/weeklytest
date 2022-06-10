package com.example.spring0607;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Spring0607Application {

    public static void main(String[] args) {
        SpringApplication.run(Spring0607Application.class, args);
    }

}
