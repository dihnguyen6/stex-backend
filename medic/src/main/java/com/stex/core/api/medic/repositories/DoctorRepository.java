package com.stex.core.api.medic.repositories;

import com.stex.core.api.medic.models.Doctor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {
    Doctor findById(ObjectId id);

    @Query
    List<Doctor> findAllByFirstNameOrderByLastName(String firstName, String lastName);

    List<Doctor> findAllByFirstNameAndLastName(String firstName, String lastName);
}
