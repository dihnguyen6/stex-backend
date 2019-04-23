package com.stex.core.api.medic.models;

import com.stex.core.api.tools.Tools;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

@Document(collection = "medicines")
public class Medicine extends ResourceSupport {

    @Id
    private ObjectId medicineId;

    private String name;

    private String description;

    private int content;

    private String manufacture;

    public Medicine(String name, String description, int content, String manufacture) {
        this.name = name;
        this.description = description;
        this.content = content;
        this.manufacture = manufacture;
    }

    public ObjectId getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(ObjectId medicineId) {
        this.medicineId = medicineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    @Override
    public String toString() {
        return Tools.toString(this);
    }
}
