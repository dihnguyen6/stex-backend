package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Medicine;
import com.stex.core.api.medic.repositories.MedicineRepository;
import com.stex.core.api.medic.services.MedicineService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MedicineRepository medicineRepository;

    public Medicine findByMedicineId(ObjectId id) {
        Medicine foundMedicine = medicineRepository.findById(id);
        LOGGER.info("Successful found Medicine {}", foundMedicine);
        return foundMedicine;
    }

    public List<Medicine> findAllMedicines() {
        LOGGER.info("Loading all medicines ...");
        return medicineRepository.findAll();
    }

    public Medicine createMedicine(Medicine medicine) {
        Medicine createMedicine = medicineRepository.save(medicine);
        LOGGER.info("Successful created Medicine {}", createMedicine);
        return createMedicine;
    }

    public void updateMedicine(Medicine medicine) {
        Medicine updateMedicine = medicineRepository.findById(medicine.getId());
        updateMedicine.setContent(medicine.getContent());
        updateMedicine.setDescription(medicine.getDescription());
        updateMedicine.setManufacture(medicine.getManufacture());
        updateMedicine.setName(medicine.getName());
        medicineRepository.save(updateMedicine);
        LOGGER.info("Successful updated Medicine {}", updateMedicine);
    }

    public void deleteMedicine(ObjectId id) {
        Medicine deleteMedicine = medicineRepository.findById(id);
        medicineRepository.delete(deleteMedicine);
        LOGGER.info("Successful deleted Medicine {}", deleteMedicine);
    }
}
