package com.stex.core.api.medic.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stex.core.api.tools.ObjectID_Serializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Document(collection = "patients")
public class Patient extends ResourceSupport {
    @Id
    private ObjectId id;

    private Information information;

    private String firstName;

    private String lastName;

    public Patient() {
    }

    @JsonSerialize(using = ObjectID_Serializer.class)
    public ObjectId getPatientId() {
        return id;
    }

    public void setPatientId(ObjectId id) {
        this.id = id;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", information=" + information +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
