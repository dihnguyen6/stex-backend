package com.stex.core.api.webapp.configurations;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mongodb")
public class MongoDbProperties {
    private MongoProperties cafe = new MongoProperties();
    private MongoProperties medic = new MongoProperties();

    public MongoDbProperties() {
    }

    public MongoProperties getCafe() {
        return cafe;
    }

    public MongoProperties getMedic() {
        return medic;
    }
}
