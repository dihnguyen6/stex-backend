package com.stex.core.api.medic.models;

import com.stex.core.api.tools.Status;
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
    private ObjectId receptionId;

    @DBRef
    private Diagnose diagnose;

    @DBRef
    private Medicine medicine;

    private int quantity;

    private String description;

    @DateTimeFormat
    private Date createdAt;

    @DateTimeFormat
    private Date updatedAt;

    private Status status;

    public Reception() {
    }

    public Reception(Diagnose diagnose, Medicine medicine, int quantity, String description) {
        this.diagnose = diagnose;
        this.medicine = medicine;
        this.quantity = quantity;
        this.description = description;
    }

    public ObjectId getReceptionId() {
        return receptionId;
    }

    public void setReceptionId(ObjectId receptionId) {
        this.receptionId = receptionId;
    }

    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
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
}
