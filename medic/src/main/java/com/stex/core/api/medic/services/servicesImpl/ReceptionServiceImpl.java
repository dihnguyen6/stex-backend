package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Reception;
import com.stex.core.api.medic.repositories.ReceptionRepository;
import com.stex.core.api.medic.services.ReceptionService;
import com.stex.core.api.tools.constants.Status;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReceptionServiceImpl implements ReceptionService {

    private final ReceptionRepository receptionRepository;

    public ReceptionServiceImpl(ReceptionRepository receptionRepository) {
        this.receptionRepository = receptionRepository;
    }

    public Reception findByReceptionId(ObjectId id) {
        return receptionRepository.findById(id);
    }

    public Reception findByDiagnoseId(ObjectId id) {
        return receptionRepository.findByDiagnoseId(id);
    }

    public List<Reception> findAllReceptions() {
        return receptionRepository.findAll();
    }

    public Reception updateReception(Reception reception) {
        return receptionRepository.save(reception);
    }
}
