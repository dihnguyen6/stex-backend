package com.stex.core.api.medic.services;

import com.stex.core.api.medic.models.Patient;
import org.bson.types.ObjectId;

import java.util.List;

public interface PatientService {
    Patient findByPatientId(ObjectId id);

    List<Patient> findAllPatients();

    List<Patient> findAllByFirstNameOrderByLastName(String firstName, String lastName);

    List<Patient> findAllByFirstNameAndLastName(String firstName, String lastName);

    Patient updatePatient(Patient patient);
}
