/*
package com.stex.api.app.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = {"com.stex.core.api.cafe.repositories"},
        mongoTemplateRef = "primaryMongoTemplate"
)
@ConfigurationProperties(prefix = "primary.mongodb")
public class PrimaryMongoConnection extends AbstractMongoConfig {

    @Primary
    @Override
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate getMongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }
}
*/
