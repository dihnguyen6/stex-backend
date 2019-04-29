package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Medicine;
import com.stex.core.api.medic.repositories.MedicineRepository;
import com.stex.core.api.medic.services.MedicineService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Autowired
    public MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Medicine findByMedicineId(ObjectId id) {
        return medicineRepository.findById(id);
    }

    public List<Medicine> findAllMedicines() {
        return medicineRepository.findAll();
    }

    public Medicine updateMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public void deleteMedicine(Medicine medicine) {
        medicineRepository.delete(medicine);
    }
}
