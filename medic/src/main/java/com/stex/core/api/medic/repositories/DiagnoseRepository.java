package com.stex.core.api.medic.repositories;

import com.stex.core.api.medic.models.Diagnose;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnoseRepository extends MongoRepository<Diagnose, String> {
    Diagnose findByDiagnoseId (ObjectId id);
    List<Diagnose> findAllByDoctorId(ObjectId id);
}
