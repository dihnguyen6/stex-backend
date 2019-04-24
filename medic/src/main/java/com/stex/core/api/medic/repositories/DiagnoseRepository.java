package com.stex.core.api.medic.repositories;

import com.stex.core.api.medic.models.Diagnose;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnoseRepository extends MongoRepository<Diagnose, String> {
    Diagnose findById(ObjectId id);

    @Query("{'doctor.id': ?0}")
    List<Diagnose> findAllByDoctor_Id(ObjectId id);

    @Query("{'patient.id': ?0}")
    List<Diagnose> findAllByPatient_Id(ObjectId id);

}
