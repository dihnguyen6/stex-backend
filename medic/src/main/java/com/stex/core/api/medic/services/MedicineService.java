package com.stex.core.api.medic.services;

import com.stex.core.api.medic.models.Medicine;
import org.bson.types.ObjectId;

import java.util.List;

public interface MedicineService {
    Medicine findByMedicineId(ObjectId id);

    List<Medicine> findAllMedicines();

    Medicine updateMedicine(Medicine medicine);

    void deleteMedicine(Medicine medicine);
}
