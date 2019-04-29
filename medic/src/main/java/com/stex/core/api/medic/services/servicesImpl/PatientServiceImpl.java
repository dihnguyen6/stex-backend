package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Patient;
import com.stex.core.api.medic.repositories.PatientRepository;
import com.stex.core.api.medic.services.PatientService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient findByPatientId(ObjectId id) {
        return patientRepository.findById(id);
    }

    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    public List<Patient> findAllByFirstNameOrderByLastName(String firstName, String lastName) {
        return patientRepository.findAllByFirstNameOrderByLastName(firstName, lastName);
    }

    public List<Patient> findAllByFirstNameAndLastName(String firstName, String lastName) {
        return patientRepository.findAllByFirstNameAndLastName(firstName, lastName);
    }

    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }
}
