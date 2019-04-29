package com.stex.core.api.medic.services;

import com.stex.core.api.medic.models.Reception;
import org.bson.types.ObjectId;

import java.util.List;

public interface ReceptionService {
    Reception findByReceptionId(ObjectId id);

    Reception findByDiagnoseId(ObjectId id);

    List<Reception> findAllReceptions();

    Reception updateReception(Reception reception);

}
