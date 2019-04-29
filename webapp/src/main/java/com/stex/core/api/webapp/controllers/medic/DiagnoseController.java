package com.stex.core.api.webapp.controllers.medic;

import com.stex.core.api.medic.models.Diagnose;
import com.stex.core.api.medic.models.Reception;
import com.stex.core.api.medic.services.DiagnoseService;
import com.stex.core.api.medic.services.ReceptionService;
import com.stex.core.api.tools.ExceptionHandler.ResourceForbiddenException;
import com.stex.core.api.tools.ExceptionHandler.ResourceNotFoundException;
import com.stex.core.api.tools.constants.Status;
import com.stex.core.api.webapp.ResourcesAssembler.MedicResource.DiagnoseResourceAssembler;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/diagnoses")
public class DiagnoseController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DiagnoseService diagnoseService;

    private final ReceptionService receptionService;

    private final DiagnoseResourceAssembler diagnoseResourceAssembler;

    @Autowired
    public DiagnoseController(DiagnoseService diagnoseService, ReceptionService receptionService, DiagnoseResourceAssembler diagnoseResourceAssembler) {
        this.diagnoseService = diagnoseService;
        this.receptionService = receptionService;
        this.diagnoseResourceAssembler = diagnoseResourceAssembler;
    }

    @GetMapping("/")
    public Resources<Resource<Diagnose>> getAllDiagnoses() {
        List<Resource<Diagnose>> diagnoses = diagnoseService.findAllDiagnoses()
                .stream()
                .map(diagnoseResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (diagnoses.isEmpty()) {
            throw new ResourceNotFoundException("Diagnose", null, null);
        }
        LOGGER.debug(diagnoseService.findAllDiagnoses().toString());
        return new Resources<>(diagnoses,
                linkTo(methodOn(DiagnoseController.class).getAllDiagnoses()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<Diagnose>> getDiagnoseById(@PathVariable ObjectId id) {
        Diagnose diagnose = diagnoseService.findByDiagnoseId(id);
        if (diagnose == null) {
            throw new ResourceNotFoundException("Diagnose", "id", id);
        }
        LOGGER.debug(diagnose.toString());
        return ResponseEntity.created(linkTo(methodOn(DiagnoseController.class)
                .getDiagnoseById(id)).toUri())
                .body(diagnoseResourceAssembler.toResource(diagnose));
    }

    @GetMapping("/doctor/{doctorId}")
    public Resources<Resource<Diagnose>> getDiagnosesByDoctor(@PathVariable ObjectId doctorId) {
        List<Resource<Diagnose>> diagnoses = diagnoseService.findAllByDoctorId(doctorId)
                .stream()
                .map(diagnoseResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (diagnoses.isEmpty()) {
            throw new ResourceNotFoundException("Diagnoses", "doctorId", doctorId);
        }
        LOGGER.debug(diagnoses.toString());
        return new Resources<>(diagnoses,
                linkTo(methodOn(DiagnoseController.class).getDiagnosesByDoctor(doctorId)).withSelfRel());
    }

    @GetMapping("/patient/{patientId}")
    public Resources<Resource<Diagnose>> getDiagnosesByPatient(@PathVariable ObjectId patientId) {
        List<Resource<Diagnose>> diagnoses = diagnoseService.findAllByPatientId(patientId)
                .stream()
                .map(diagnoseResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (diagnoses.isEmpty()) {
            throw new ResourceNotFoundException("Diagnoses", "patientId", patientId);
        }
        LOGGER.debug(diagnoses.toString());
        return new Resources<>(diagnoses,
                linkTo(methodOn(DiagnoseController.class).getDiagnosesByPatient(patientId)).withSelfRel());
    }

    @PostMapping("/")
    public ResponseEntity<ResourceSupport> createDiagnose(@RequestBody Diagnose diagnose) {
        diagnose.setReceptions(new ArrayList<>());
        diagnose.setStatus(Status.IN_PROGRESS);
        diagnose.setCreatedAt(new Date());
        diagnose.setUpdatedAt(new Date());
        Diagnose createDiagnose = diagnoseService.updateDiagnose(diagnose);
        LOGGER.debug("Successful created Diagnose:{}", createDiagnose);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createDiagnose.getDiagnoseId()).toUri();
        return ResponseEntity.created(location)
                .body(diagnoseResourceAssembler.toResource(createDiagnose));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<ResourceSupport> updateStatusDiagnose(@PathVariable ObjectId id, @PathVariable String status) {
        Diagnose diagnose = validateModifiyable(id);
        diagnose.setUpdatedAt(new Date());
        if (status.equals("complete")) {
            diagnose.setStatus(Status.COMPLETED);
        } else if (status.equals("cancel")) {
            diagnose.setStatus(Status.CANCELLED);
        }
        diagnoseService.updateDiagnose(diagnose);
        LOGGER.debug("Successful {} Diagnose with [id: {}]{}", status, id, diagnose);
        return ResponseEntity.ok(diagnoseResourceAssembler.toResource(diagnose));
    }

    @PutMapping("/{id}/add/{receptionId}")
    public ResponseEntity<ResourceSupport> addReceptionToDiagnose(@PathVariable ObjectId id
            , @PathVariable ObjectId receptionId) {
        Diagnose diagnose = validateModifiyable(id);
        diagnose.setUpdatedAt(new Date());
        List<Reception> receptions = diagnose.getReceptions();
        Reception createReception = receptionService.findByReceptionId(receptionId);
        receptions.add(createReception);
        diagnose.setReceptions(receptions);
        diagnoseService.updateDiagnose(diagnose);
        LOGGER.debug("Successful added Reception [id: {}] to Diagnose [id: {}]", receptionId, id);
        return ResponseEntity.ok(diagnoseResourceAssembler.toResource(diagnose));
    }

    private Diagnose validateModifiyable(@PathVariable ObjectId id) {
        Diagnose diagnose = diagnoseService.findByDiagnoseId(id);
        if (diagnose == null) throw new ResourceNotFoundException("Diagnose", "id", id);
        if (diagnose.getStatus() != Status.IN_PROGRESS)
            throw new ResourceForbiddenException("Diagnose", "status", diagnose.getStatus());
        return diagnose;
    }
}
