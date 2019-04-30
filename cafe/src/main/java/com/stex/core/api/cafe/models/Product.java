package com.stex.core.api.cafe.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stex.core.api.tools.ObjectID_Serializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

@Document(collection = "products")
public class Product extends ResourceSupport {
    @Id
    private ObjectId id;

    private String name;

    private double preis;

    @DBRef
    private Category category;

    public Product() {
    }

    public Product(String name, double preis, Category category) {
        this.name = name;
        this.preis = preis;
        this.category = category;
    }

    @JsonSerialize(using = ObjectID_Serializer.class)
    public ObjectId getProductId() {
        return id;
    }

    public void setProductId(ObjectId id) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", preis=" + preis +
                ", category=" + category +
                '}';
    }
}
