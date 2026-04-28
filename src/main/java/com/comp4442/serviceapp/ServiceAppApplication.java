package com.comp4442.serviceapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ServiceAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAppApplication.class, args);
    }
}