/*
package com.stex.api.app.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = {"com.stex.core.api.medic.repositories"},
        mongoTemplateRef = "secondaryMongoTemplate"
)
@ConfigurationProperties(prefix = "secondary.mongodb")
public class SecondaryMongoConnection extends AbstractMongoConfig {

    @Override
    @Bean(name = "secondaryMongoTemplate")
    public MongoTemplate getMongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }
}
*/
