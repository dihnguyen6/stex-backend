package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Medicine;
import com.stex.core.api.medic.services.MedicineService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {
    public Medicine findByMedicineId(ObjectId id) {
        return null;
    }

    public List<Medicine> findAllMedicines() {
        return null;
    }

    public Medicine createMedicine(Medicine medicine) {
        return null;
    }

    public void updateMedicine(Medicine medicine) {

    }

    public void deleteMedicine(ObjectId id) {

    }
}
