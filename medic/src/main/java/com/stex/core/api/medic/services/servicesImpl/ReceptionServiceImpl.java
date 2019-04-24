package com.stex.core.api.medic.services.servicesImpl;

import com.stex.core.api.medic.models.Reception;
import com.stex.core.api.medic.repositories.ReceptionRepository;
import com.stex.core.api.medic.services.ReceptionService;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReceptionServiceImpl implements ReceptionService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReceptionRepository receptionRepository;

    public Reception findByReceptionId(ObjectId id) {
        Reception foundReception = receptionRepository.findByReceptionId(id);
        LOGGER.info("Success found Reception {}", foundReception);
        return foundReception;
    }

    public Reception findByDiagnoseId(ObjectId id) {
        Reception foundReception = receptionRepository.findByDiagnoseId(id);
        LOGGER.info("Successful found Reception {}", foundReception);
        return foundReception;
    }

    public List<Reception> findAllReceptions() {
        LOGGER.info("Loading all Receptions ...");
        return receptionRepository.findAll();
    }

    public Reception createReception(Reception reception) {
        Reception createReception = receptionRepository.save(reception);
        LOGGER.info("Successful created Reception {}", createReception);
        return createReception;
    }

    public void updateReception(Reception reception) {
        Reception updateReception = receptionRepository.findByReceptionId(reception.getReceptionId());
        updateReception.setDescription(reception.getDescription());
        updateReception.setMedicine(reception.getMedicine());
        updateReception.setQuantity(reception.getQuantity());
        updateReception.setUpdatedAt(new Date());
        receptionRepository.save(updateReception);
        LOGGER.info("Successful updated Reception {}", updateReception);
    }

    public void completeReception(ObjectId id) {
        Reception completeReception = receptionRepository.findByReceptionId(id);
        completeReception.setUpdatedAt(new Date());
        completeReception.setStatus(Status.COMPLETED);
        receptionRepository.save(completeReception);
        LOGGER.info("Successful completed Reception {}", completeReception);
    }
}
