package com.stex.core.api.cafe.models;

import com.stex.core.api.tools.Tools;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

@Document(collection = "products")
public class Product extends ResourceSupport {
    @Id
    private ObjectId id;

    private String name;
    private double preis;

    public Product() {
    }

    public Product(String name, double preis) {
        this.name = name;
        this.preis = preis;
    }

    public ObjectId getProductId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    @Override
    public String toString() {
        return Tools.toString(this);
    }
}
