package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Doctor;
import com.stex.core.api.medic.repositories.DoctorRepository;
import com.stex.core.api.medic.services.DoctorService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor findByDoctorId(ObjectId id) {
        return doctorRepository.findById(id);
    }

    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> findAllDoctorsByFirstNameOrderByLastName(String firstName, String lastName) {
        return doctorRepository.findAllByFirstNameOrderByLastName(firstName, lastName);
    }

    public List<Doctor> findAllDoctorsByFirstNameAndLastName(String firstName, String lastName) {
        return doctorRepository.findAllByFirstNameAndLastName(firstName, lastName);
    }

    public Doctor updateDoctor(Doctor doctor) {
       return doctorRepository.save(doctor);
    }
}
