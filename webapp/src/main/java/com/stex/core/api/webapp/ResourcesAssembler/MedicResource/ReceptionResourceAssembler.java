package com.stex.core.api.webapp.ResourcesAssembler.MedicResource;

import com.stex.core.api.medic.models.Reception;
import com.stex.core.api.tools.constants.Status;
import com.stex.core.api.webapp.controllers.medic.MedicineController;
import com.stex.core.api.webapp.controllers.medic.ReceptionContoller;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class ReceptionResourceAssembler implements ResourceAssembler<Reception, Resource<Reception>> {
    @Override
    public Resource<Reception> toResource(Reception r) {
        Resource<Reception> resource = new Resource<>(r,
                linkTo(methodOn(ReceptionContoller.class).getAllReception()).withRel("receptions"),
                linkTo(methodOn(ReceptionContoller.class).getReceptionById(r.getReceptionId())).withSelfRel());

        if (r.getStatus() == Status.IN_PROGRESS) {
            resource.add(linkTo(methodOn(ReceptionContoller.class)
                    .updateStatusReception(r.getReceptionId(), "complete")).withRel("complete"));
            resource.add(linkTo(methodOn(ReceptionContoller.class)
                    .updateStatusReception(r.getReceptionId(), "cancel")).withRel("cancel"));
        }

        r.getMedicine().add(linkTo(methodOn(MedicineController.class)
                .getMedicineById(r.getMedicine().getMedicineId()))
                .withSelfRel());
        return resource;
    }
}
