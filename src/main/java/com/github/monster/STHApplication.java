package com.github.monster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.github.monster.*")
public class STHApplication {

    public static void main(String[] args) {
        SpringApplication.run(STHApplication.class, args);
    }
}
