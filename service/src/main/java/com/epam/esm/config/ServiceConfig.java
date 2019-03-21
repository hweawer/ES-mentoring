package com.epam.esm.config;

import com.epam.esm.service.application.DatabaseSpecificationCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.epam.esm")
public class ServiceConfig {
    @Bean
    public DatabaseSpecificationCreator specificationCreator(){
        return new DatabaseSpecificationCreator();
    }
}
