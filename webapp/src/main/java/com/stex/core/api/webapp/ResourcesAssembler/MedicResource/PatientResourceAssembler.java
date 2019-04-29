package com.stex.core.api.webapp.ResourcesAssembler.MedicResource;

import com.stex.core.api.medic.models.Patient;
import com.stex.core.api.webapp.controllers.medic.PatientController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class PatientResourceAssembler implements ResourceAssembler<Patient, Resource<Patient>> {
    @Override
    public Resource<Patient> toResource(Patient p) {
        return new Resource<>(p,
                linkTo(methodOn(PatientController.class).getAllPatients()).withRel("patients"),
                linkTo(methodOn(PatientController.class).getPatientById(p.getPatientId())).withSelfRel());
    }
}
