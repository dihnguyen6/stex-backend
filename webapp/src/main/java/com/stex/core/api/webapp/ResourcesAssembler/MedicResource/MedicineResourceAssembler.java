package com.stex.core.api.webapp.ResourcesAssembler.MedicResource;

import com.stex.core.api.medic.models.Medicine;
import com.stex.core.api.webapp.controllers.medic.MedicineController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class MedicineResourceAssembler implements ResourceAssembler<Medicine, Resource<Medicine>> {

    @Override
    public Resource<Medicine> toResource(Medicine m) {
        return new Resource<>(m,
                linkTo(methodOn(MedicineController.class).getAllMedicines()).withRel("medicines"),
                linkTo(methodOn(MedicineController.class).getMedicineById(m.getMedicineId())).withSelfRel());
    }
}
