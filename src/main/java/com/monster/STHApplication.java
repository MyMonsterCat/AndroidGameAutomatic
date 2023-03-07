package com.monster;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.monster.*")
public class STHApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(STHApplication.class).headless(false).run(args);
    }
}
