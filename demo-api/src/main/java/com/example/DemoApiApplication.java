package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableJpaAuditing
@SpringBootApplication
//@EntityScan(basePackages = "com.example.demoservice.model")
//@EnableJpaRepositories(basePackages = "com.example.demoservice.repository")
public class DemoApiApplication {

    public static void main(String[] args) {
        /*
         * TODO Rex 使用 spring-boot-devtools 會導致DemoApiApplication
         * 執行兩次 不知道是否是正常行為 待研究
         */
        System.out.println("DemoApiApplication run");
        SpringApplication.run(DemoApiApplication.class, args);
        System.out.println("DemoApiApplication ok");

    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
