package com.stex.core.api.medic.repositories;

import com.stex.core.api.medic.models.Medicine;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends MongoRepository<Medicine, String> {
    Medicine findById(ObjectId id);
}
