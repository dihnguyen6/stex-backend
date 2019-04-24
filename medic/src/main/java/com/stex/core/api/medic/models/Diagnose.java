package com.stex.core.api.medic.models;

import com.stex.core.api.tools.Status;
import com.stex.core.api.tools.Tools;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Document(collection = "diagnoses")
public class Diagnose {

    @Id
    private ObjectId id;

    @DBRef
    private Doctor doctor;

    @DBRef
    private List<Reception> receptions;

    @DBRef
    private Patient patient;

    @DateTimeFormat
    private Date createdAt;

    @DateTimeFormat
    private Date updatedAt;

    private String description;

    private Status status;

    public Diagnose() {
    }

    public Diagnose(Doctor doctor, List<Reception> receptions, String description) {
        this.doctor = doctor;
        this.receptions = receptions;
        this.description = description;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<Reception> getReceptions() {
        return receptions;
    }

    public void setReceptions(List<Reception> receptions) {
        this.receptions = receptions;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return Tools.toString(this);
    }
}
