package com.example.exampleproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class FootballDataApplication{

    public static void main(String[] args) {
        SpringApplication.run(FootballDataApplication.class, args);
    }
}
