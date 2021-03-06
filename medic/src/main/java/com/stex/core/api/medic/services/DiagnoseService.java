package com.stex.core.api.medic.services;

import com.stex.core.api.medic.models.Diagnose;
import org.bson.types.ObjectId;

import java.util.List;

public interface DiagnoseService {
    Diagnose findByDiagnoseId(ObjectId id);

    List<Diagnose> findAllByDoctorId(ObjectId id);

    List<Diagnose> findAllDiagnoses();

    List<Diagnose> findAllByPatientId(ObjectId patientId);

    Diagnose updateDiagnose(Diagnose diagnose);
}
