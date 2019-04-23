package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Reception;
import com.stex.core.api.medic.services.ReceptionService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceptionServiceImpl implements ReceptionService {
    public Reception findByReceptionId(ObjectId id) {
        return null;
    }

    public Reception findByDiagnoseId(ObjectId id) {
        return null;
    }

    public List<Reception> findAllDiagnoses() {
        return null;
    }

    public Reception createReception(Reception reception) {
        return null;
    }

    public void updateReception(Reception reception) {

    }

    public void completeReception(ObjectId id) {

    }
}
