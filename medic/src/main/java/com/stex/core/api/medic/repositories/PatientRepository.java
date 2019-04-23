package com.stex.core.api.medic.repositories;

import com.stex.core.api.medic.models.Patient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
    Patient findByPatientId(ObjectId id);
    List<Patient> findAllByFirstNameOrderByLastName(String firstName, String lastName);
}
