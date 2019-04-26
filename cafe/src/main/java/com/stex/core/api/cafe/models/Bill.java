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
import java.util.List;

@Document(collection = "bills")
public class Bill extends ResourceSupport {

    @Id
    private ObjectId id;

    @DBRef
    private List<Order> orders;

    private double preis;

    private Status status;

    private int table;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date createdAt;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date updatedAt;

    public Bill() {

    }

    @JsonSerialize(using = ObjectID_Serializer.class)
    public ObjectId getBillId() {
        return id;
    }

    public void setBillId(ObjectId id) {
        this.id = id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
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
        return "\nBill {\n" +
                "\t\"id\": " + id +
                ",\n\t\"orders\": " + orders +
                ",\n\t\"preis\": " + preis +
                ",\n\t\"status\": " + status +
                ",\n\t\"table\": " + table +
                ",\n\t\"createdAt\": " + createdAt +
                ",\n\t\"updatedAt\": " + updatedAt +
                "\n}";
    }
}
