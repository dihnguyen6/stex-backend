package com.stex.api.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.stex.core.api.cafe", "com.stex.core.api.medic"})
@EntityScan(basePackages = {"com.stex.core.api.cafe.models", "com.stex.core.api.medic.models"})
@EnableMongoRepositories(basePackages = {"com.stex.core.api.cafe.repositories", "com.stex.core.api.medic.repositories"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
