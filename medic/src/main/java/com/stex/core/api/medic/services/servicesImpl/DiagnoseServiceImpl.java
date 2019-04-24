package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Diagnose;
import com.stex.core.api.medic.repositories.DiagnoseRepository;
import com.stex.core.api.medic.services.DiagnoseService;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiagnoseServiceImpl implements DiagnoseService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DiagnoseRepository diagnoseRepository;

    public Diagnose findByDiagnoseId(ObjectId id) {
        Diagnose diagnose = diagnoseRepository.findByDiagnoseId(id);
        LOGGER.info("Successful found Diagnose {}", diagnose);
        return diagnose;
    }

    public List<Diagnose> findAllByDoctorId(ObjectId id) {
        List<Diagnose> result = diagnoseRepository.findAllByDoctor_Id(id);
        return result;
    }

    public List<Diagnose> findAllDiagnoses() {
        List<Diagnose> diagnoses = diagnoseRepository.findAll();
        LOGGER.info("Loading all diagnoses ...");
        return diagnoses;
    }

    public List<Diagnose> findAllByPatientId(ObjectId id) {
        List<Diagnose> diagnoses = diagnoseRepository.findAllByPatient_Id(id);
        LOGGER.info("Loading all Diagnose by Patient's Id {}", id);
        return diagnoses;
    }

    public Diagnose createDiagnose(Diagnose diagnose) {
        diagnose.setCreatedAt(new Date());
        diagnose.setUpdatedAt(new Date());
        diagnose.setStatus(Status.IN_PROGRESS);
        Diagnose createDiagnose = diagnoseRepository.save(diagnose);
        LOGGER.info("Successful created Diagnose {}", createDiagnose);
        return createDiagnose;
    }

    public void updateDiagnose(Diagnose diagnose) {
        Diagnose updateDiagnose = diagnoseRepository.findByDiagnoseId(diagnose.getDiagnoseId());
        updateDiagnose.setUpdatedAt(new Date());
        updateDiagnose.setDescription(diagnose.getDescription());
        updateDiagnose.setReceptions(diagnose.getReceptions());
        diagnoseRepository.save(updateDiagnose);
        LOGGER.info("Successful updated Diagnose {}", updateDiagnose);
    }

    public void completeDiagnose(ObjectId id) {
        Diagnose completeDiagnose = diagnoseRepository.findByDiagnoseId(id);
        completeDiagnose.setStatus(Status.COMPLETED);
        completeDiagnose.setUpdatedAt(new Date());
        diagnoseRepository.save(completeDiagnose);
        LOGGER.info("Successful complete Diagnose {}", completeDiagnose);
    }
}
