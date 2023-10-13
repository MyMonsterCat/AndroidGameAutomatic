package com.github.monster.mga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.github.monster.mga.*")
public class MGAApplication {

    public static void main(String[] args) {
        SpringApplication.run(MGAApplication.class, args);
    }
}
