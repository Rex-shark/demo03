package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@EnableJpaAuditing
@SpringBootApplication
public class DemoApiApplication {

    public static void main(String[] args) {
        System.out.println("Demo api run");
        SpringApplication.run(DemoApiApplication.class, args);
    }

}
