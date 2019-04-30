package com.stex.core.api.webapp.controllers.medic;

import com.stex.core.api.medic.models.Patient;
import com.stex.core.api.medic.services.PatientService;
import com.stex.core.api.tools.ExceptionHandler.ResourceNotFoundException;
import com.stex.core.api.webapp.ResourcesAssembler.MedicResource.PatientResourceAssembler;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/patients", produces = "application/hal+json")
public class PatientController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PatientService patientService;

    private final PatientResourceAssembler patientResourceAssembler;

    @Autowired
    public PatientController(PatientService patientService,
                             PatientResourceAssembler patientResourceAssembler) {
        this.patientService = patientService;
        this.patientResourceAssembler = patientResourceAssembler;
    }

    @GetMapping("/")
    public Resources<Resource<Patient>> getAllPatients() {
        List<Resource<Patient>> resources = patientService.findAllPatients()
                .stream()
                .map(patientResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (resources.isEmpty())
            throw new ResourceNotFoundException("Patients", null, null);
        LOGGER.debug(resources.toString());
        return new Resources<>(resources,
                linkTo(methodOn(PatientController.class).getAllPatients()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<Patient>> getPatientById(@PathVariable ObjectId id) {
        Patient patient = patientService.findByPatientId(id);
        if (patient == null) throw new ResourceNotFoundException("Patient", "id", id);
        LOGGER.debug("Successful found Patient:{}", patient);
        return ResponseEntity.created(linkTo(methodOn(PatientController.class)
                .getPatientById(id)).toUri())
                .body(patientResourceAssembler.toResource(patient));
    }

    @PostMapping("/")
    public ResponseEntity<Resource<Patient>> createPatient(@RequestBody Patient patient) {
        Patient createPatient = patientService.updatePatient(patient);
        LOGGER.debug("Successful created Patient:{}", patient);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createPatient.getPatientId()).toUri();
        return ResponseEntity.created(location)
                .body(patientResourceAssembler.toResource(createPatient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceSupport> updatePatient(@PathVariable ObjectId id, @RequestBody Patient patient) {
        Patient updatePatient = patientService.findByPatientId(id);
        if (updatePatient == null)
            throw new ResourceNotFoundException("Patient", "id", id);
        if (patient.getFirstName() != null) updatePatient.setFirstName(patient.getFirstName());
        if (patient.getLastName() != null) updatePatient.setLastName(patient.getLastName());
        if (patient.getInformation() != null) updatePatient.setInformation(patient.getInformation());
        patientService.updatePatient(updatePatient);
        LOGGER.debug("Successful updated Patient: {}", updatePatient);
        return ResponseEntity.ok(patientResourceAssembler.toResource(updatePatient));
    }
}
