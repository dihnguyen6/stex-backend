package com.stex.core.api.medic.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stex.core.api.tools.ObjectID_Serializer;
import com.stex.core.api.tools.constants.MedicineType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

@Document(collection = "medicines")
public class Medicine extends ResourceSupport {

    @Id
    private ObjectId id;

    private String name;

    private String description;

    private int content;

    private String manufacture;

    private MedicineType type;

    public Medicine() {
    }

    @JsonSerialize(using = ObjectID_Serializer.class)
    public ObjectId getMedicineId() {
        return id;
    }

    public void setMedicineId(ObjectId id) {
        this.id = id;
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

    public MedicineType getType() {
        return type;
    }

    public void setType(MedicineType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", content=" + content +
                ", manufacture='" + manufacture + '\'' +
                ", type=" + type +
                '}';
    }
}
