package com.stex.core.api.cafe.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stex.core.api.tools.ObjectID_Serializer;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

@Document(collection = "orders")
public class Order extends ResourceSupport {

    @Id
    private ObjectId id;

    @DBRef
    private Product product;

    private int quantity;

    private Status status;

    private String description;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date createdAt;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date updatedAt;

    public Order() {

    }

    @JsonSerialize(using = ObjectID_Serializer.class)
    public ObjectId getOrderId() {
        return id;
    }

    public void setOrderId(ObjectId id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "\nOrder {\n" +
                "\t\"id\": " + id +
                ",\n\t\"product\": " + product +
                //",\n\t\"bill\": " + bill +
                ",\n\t\"quantity\": " + quantity +
                ",\n\t\"status\": " + status +
                ",\n\t\"description\": '" + description + '\'' +
                ",\n\t\"createdAt\": " + createdAt +
                ",\n\t\"updatedAt\": " + updatedAt +
                "\n}";
    }
}
