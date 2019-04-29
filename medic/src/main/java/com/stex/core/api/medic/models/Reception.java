package com.stex.core.api.medic.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stex.core.api.tools.ObjectID_Serializer;
import com.stex.core.api.tools.constants.Status;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

@Document(collection = "receptions")
public class Reception extends ResourceSupport {

    @Id
    private ObjectId id;

    @DBRef
    private Medicine medicine;

    private int quantity;

    private String description;

    private Status status;

    @DateTimeFormat
    private Date createdAt;

    @DateTimeFormat
    private Date updatedAt;

    public Reception() {
    }

    @JsonSerialize(using = ObjectID_Serializer.class)
    public ObjectId getReceptionId() {
        return id;
    }

    public void setReceptionId(ObjectId id) {
        this.id = id;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reception{" +
                "id=" + id +
                ", medicine=" + medicine +
                ", quantity=" + quantity +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
