package com.stex.core.api.medic.models;

import com.stex.core.api.tools.Tools;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Document(collection = "patients")
public class Patient extends ResourceSupport {
    @Id
    private ObjectId patientId;

    @DBRef
    private Information information;

    @DBRef
    private List<Diagnose> diagnoses;

    private String firstName;

    private String lastName;

    public Patient() {
    }

    public Patient(Information information, List<Diagnose> diagnoses, String firstName, String lastName) {
        this.information = information;
        this.diagnoses = diagnoses;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ObjectId getPatientId() {
        return patientId;
    }

    public void setPatientId(ObjectId patientId) {
        this.patientId = patientId;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public List<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(List<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
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
