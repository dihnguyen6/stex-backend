package com.stex.core.api.medic.services;

import com.stex.core.api.medic.models.Reception;
import org.bson.types.ObjectId;

import java.util.List;

public interface ReceptionService {
    Reception findByReceptionId(ObjectId id);
    Reception findByDiagnoseId(ObjectId id);
    List<Reception> findAllDiagnoses();

    Reception createReception(Reception reception);
    void updateReception(Reception reception);
    void completeReception(ObjectId id);
}
