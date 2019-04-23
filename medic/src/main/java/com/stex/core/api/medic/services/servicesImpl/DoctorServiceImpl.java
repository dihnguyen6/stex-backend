package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Doctor;
import com.stex.core.api.medic.services.DoctorService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    public Doctor findByDoctorId(ObjectId id) {
        return null;
    }

    public List<Doctor> findAllDoctors() {
        return null;
    }

    public List<Doctor> findAllDoctorsByFirstNameOrderByLastName(String firstName, String lastName) {
        return null;
    }

    public List<Doctor> findAllDoctorsByFirstNameAndLastName(String firstName, String lastName) {
        return null;
    }

    public Doctor createDoctor(Doctor doctor) {
        return null;
    }

    public void updateDoctor(Doctor doctor) {

    }
}
