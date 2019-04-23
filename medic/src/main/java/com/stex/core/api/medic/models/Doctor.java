package com.stex.core.api.medic.models;

import com.stex.core.api.tools.Tools;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

@Document(collection = "doctors")
public class Doctor extends ResourceSupport {
    @Id
    private ObjectId doctorId;

    @DBRef
    private Information information;

    private String firstName;

    private String lastName;

    public Doctor() {
    }

    public Doctor(Information information, String firstName, String lastName) {
        this.information = information;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ObjectId getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(ObjectId doctorId) {
        this.doctorId = doctorId;
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
        return Tools.toString(this);
    }
}
