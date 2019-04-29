package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Diagnose;
import com.stex.core.api.medic.models.Patient;
import com.stex.core.api.medic.repositories.DiagnoseRepository;
import com.stex.core.api.medic.repositories.PatientRepository;
import com.stex.core.api.medic.services.DiagnoseService;
import com.stex.core.api.tools.constants.Status;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiagnoseServiceImpl implements DiagnoseService {

    private final DiagnoseRepository diagnoseRepository;

    @Autowired
    public DiagnoseServiceImpl(DiagnoseRepository diagnoseRepository) {
        this.diagnoseRepository = diagnoseRepository;
    }

    public Diagnose findByDiagnoseId(ObjectId id) {
        return diagnoseRepository.findById(id);
    }

    public List<Diagnose> findAllByDoctorId(ObjectId id) {
        return diagnoseRepository.findAllByDoctor_Id(id);
    }

    public List<Diagnose> findAllDiagnoses() {
        return diagnoseRepository.findAll();
    }

    public List<Diagnose> findAllByPatientId(ObjectId patientId) {
        return diagnoseRepository.findAllByPatient_Id(patientId);
    }

    public Diagnose updateDiagnose(Diagnose diagnose) {
        return diagnoseRepository.save(diagnose);
    }

}
