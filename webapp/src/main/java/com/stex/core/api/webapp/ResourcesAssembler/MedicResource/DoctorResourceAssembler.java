package com.stex.core.api.webapp.ResourcesAssembler.MedicResource;

import com.stex.core.api.medic.models.Doctor;
import com.stex.core.api.webapp.controllers.medic.DoctorController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class DoctorResourceAssembler implements ResourceAssembler<Doctor, Resource<Doctor>> {

    @Override
    public Resource<Doctor> toResource(Doctor d) {
        return new Resource<>(d,
                linkTo(methodOn(DoctorController.class).getAllDoctors()).withRel("doctors"),
                linkTo(methodOn(DoctorController.class).getDoctorById(d.getDoctorId())).withSelfRel());
    }
}
