package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Diagnose;
import com.stex.core.api.medic.services.DiagnoseService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnoseServiceImpl implements DiagnoseService {
    public Diagnose findByDiagnoseId(ObjectId id) {
        return null;
    }

    public List<Diagnose> findAllByDoctorId(ObjectId id) {
        return null;
    }

    public List<Diagnose> findAllDiagnoses() {
        return null;
    }

    public Diagnose createDiagnose(Diagnose diagnose) {
        return null;
    }

    public void updateDiagnose(Diagnose diagnose) {

    }

    public void completeDiagnose(ObjectId id) {

    }
}
