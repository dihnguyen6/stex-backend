package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Patient;
import com.stex.core.api.medic.repositories.DiagnoseRepository;
import com.stex.core.api.medic.repositories.PatientRepository;
import com.stex.core.api.medic.services.PatientService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DiagnoseRepository diagnoseRepository;

    public Patient findByPatientId(ObjectId id) {
        Patient foundPatient = patientRepository.findById(id);
        LOGGER.info("Success found Patient {}", foundPatient);
        return foundPatient;
    }

    public List<Patient> findAllPatients() {
        LOGGER.info("Loading all Patients ...");
        return patientRepository.findAll();
    }

    public List<Patient> findAllByFirstNameOrderByLastName(String firstName, String lastName) {
        LOGGER.info("Loading all Patients is {} or {}", lastName, firstName);
        return patientRepository.findAllByFirstNameOrderByLastName(firstName, lastName);
    }

    public List<Patient> findAllByFirstNameAndLastName(String firstName, String lastName) {
        LOGGER.info("Loading all Patients is {} {}", lastName, firstName);
        return patientRepository.findAllByFirstNameAndLastName(firstName, lastName);
    }

    public Patient createPatient(Patient patient) {
        Patient createPatient = patientRepository.save(patient);
        LOGGER.info("Successful created Patient {}", createPatient);
        return createPatient;
    }

    public void updatePatient(Patient patient) {
        Patient updatePatient = patientRepository.findById(patient.getId());
        updatePatient.setFirstName(patient.getFirstName());
        updatePatient.setLastName(patient.getLastName());
        updatePatient.setInformation(patient.getInformation());
        updatePatient.getDiagnoses().addAll(patient.getDiagnoses());
        patientRepository.save(updatePatient);
        LOGGER.info("Successful updated Patient {}", updatePatient);
    }
}
