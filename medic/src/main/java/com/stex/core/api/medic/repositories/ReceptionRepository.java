package com.stex.core.api.medic.repositories;

import com.stex.core.api.medic.models.Reception;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptionRepository extends MongoRepository<Reception, String> {
    Reception findByReceptionId(ObjectId id);

    @Query("{'diagnose.id': ?0}")
    Reception findByDiagnoseId(ObjectId id);
}
