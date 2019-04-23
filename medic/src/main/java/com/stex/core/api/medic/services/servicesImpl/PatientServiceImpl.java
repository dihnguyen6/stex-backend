package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Patient;
import com.stex.core.api.medic.services.PatientService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    public Patient findByPatientId(ObjectId id) {
        return null;
    }

    public List<Patient> findAllPatients() {
        return null;
    }

    public List<Patient> findAllByFirstNameOrderByLastName(String firstName, String lastName) {
        return null;
    }

    public Patient findByDiagnoseId(ObjectId id) {
        return null;
    }

    public Patient createPatient(Patient patient) {
        return null;
    }

    public void updatePatient(Patient patient) {

    }
}
