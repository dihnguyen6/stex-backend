package com.stex.core.api.webapp.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static com.stex.core.api.webapp.configurations.CafeMongoConfig.MONGO_TEMPLATE;

@Configuration
@EnableMongoRepositories(basePackages = "com.stex.core.api.cafe.repositories",
        mongoTemplateRef = MONGO_TEMPLATE)
public class CafeMongoConfig {
    protected static final String MONGO_TEMPLATE = "cafeMongoTemplate";
}
