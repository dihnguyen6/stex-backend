package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Doctor;
import com.stex.core.api.medic.repositories.DoctorRepository;
import com.stex.core.api.medic.services.DoctorService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor findByDoctorId(ObjectId id) {
        Doctor foundDoctor = doctorRepository.findById(id);
        LOGGER.info("Successful found Doctor {}", foundDoctor);
        return foundDoctor;
    }

    public List<Doctor> findAllDoctors() {
        LOGGER.info("Loading all Doctors ...");
        return doctorRepository.findAll();
    }

    public List<Doctor> findAllDoctorsByFirstNameOrderByLastName(String firstName, String lastName) {
        LOGGER.info("Loading all Doctors is {} or {}", lastName, firstName);
        return doctorRepository.findAllByFirstNameOrderByLastName(firstName, lastName);
    }

    public List<Doctor> findAllDoctorsByFirstNameAndLastName(String firstName, String lastName) {
        LOGGER.info("Loading all Doctors is {} {}", lastName, firstName);
        return doctorRepository.findAllByFirstNameAndLastName(firstName, lastName);
    }

    public Doctor createDoctor(Doctor doctor) {
        Doctor createDoctor = doctorRepository.save(doctor);
        LOGGER.info("Successful created Doctor {}", createDoctor);
        return createDoctor;
    }

    public void updateDoctor(Doctor doctor) {
        Doctor updateDoctor = doctorRepository.findById(doctor.getId());
        updateDoctor.setFirstName(doctor.getFirstName());
        updateDoctor.setLastName(doctor.getLastName());
        updateDoctor.setInformation(doctor.getInformation());
        doctorRepository.save(updateDoctor);
        LOGGER.info("Successful updated Doctor {}", updateDoctor);
    }
}
