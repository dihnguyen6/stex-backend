/*
package com.stex.api.app.configurations;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

public abstract class AbstractMongoConfig {

    private String host;
    private String database;
    private int port;

    public @Bean MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(getMongoClient(), database);
    }

    private MongoClient getMongoClient() {
        return new MongoClient(host, port);
    }

    abstract public MongoTemplate getMongoTemplate();
}
*/
