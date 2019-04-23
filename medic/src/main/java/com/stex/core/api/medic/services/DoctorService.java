package com.stex.core.api.medic.services;

import com.stex.core.api.medic.models.Doctor;
import org.bson.types.ObjectId;

import java.util.List;

public interface DoctorService {
    Doctor findByDoctorId(ObjectId id);
    List<Doctor> findAllDoctors();
    List<Doctor> findAllDoctorsByFirstNameOrderByLastName(String firstName, String lastName);
    List<Doctor> findAllDoctorsByFirstNameAndLastName(String firstName, String lastName);

    Doctor createDoctor(Doctor doctor);
    void updateDoctor(Doctor doctor);
}
