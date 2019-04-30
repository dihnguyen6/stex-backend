package com.stex.core.api.webapp.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static com.stex.core.api.webapp.configurations.MedicMongoConfig.MONGO_TEMPLATE;

@Configuration
@EnableMongoRepositories(basePackages = "com.stex.core.api.medic.repositories",
    mongoTemplateRef = MONGO_TEMPLATE)
public class MedicMongoConfig {
    protected static final String MONGO_TEMPLATE = "medicMongoTemplate";
}
