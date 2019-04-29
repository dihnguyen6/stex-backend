package com.stex.core.api.webapp.ResourcesAssembler.MedicResource;

import com.stex.core.api.medic.models.Diagnose;
import com.stex.core.api.tools.constants.Status;
import com.stex.core.api.webapp.controllers.medic.DiagnoseController;
import com.stex.core.api.webapp.controllers.medic.DoctorController;
import com.stex.core.api.webapp.controllers.medic.PatientController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class DiagnoseResourceAssembler implements ResourceAssembler<Diagnose, Resource<Diagnose>> {
    @Override
    public Resource<Diagnose> toResource(Diagnose d) {
        Resource<Diagnose> resource = new Resource<>(d,
                linkTo(methodOn(DiagnoseController.class).getAllDiagnoses()).withRel("diagnoses"),
                linkTo(methodOn(DiagnoseController.class).getDiagnoseById(d.getDiagnoseId())).withSelfRel());

        if (d.getStatus() == Status.IN_PROGRESS) {
            resource.add(linkTo(methodOn(DiagnoseController.class)
                    .updateStatusDiagnose(d.getDiagnoseId(), "complete")).withRel("complete"));
            resource.add(linkTo(methodOn(DiagnoseController.class)
                    .updateStatusDiagnose(d.getDiagnoseId(), "cancel")).withRel("cancel"));
        }

        d.getDoctor().add(linkTo(methodOn(DoctorController.class)
                .getDoctorById(d.getDoctor().getDoctorId())).withSelfRel());

        d.getPatient().add(linkTo(methodOn(PatientController.class)
                .getPatientById(d.getPatient().getPatientId())).withSelfRel());
        return resource;
    }
}
