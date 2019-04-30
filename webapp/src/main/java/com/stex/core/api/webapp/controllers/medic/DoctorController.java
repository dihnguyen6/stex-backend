package com.stex.core.api.webapp.controllers.medic;

import com.stex.core.api.medic.models.Doctor;
import com.stex.core.api.medic.services.DoctorService;
import com.stex.core.api.tools.ExceptionHandler.ResourceNotFoundException;
import com.stex.core.api.webapp.ResourcesAssembler.MedicResource.DoctorResourceAssembler;
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
@RequestMapping(value = "/api/doctors", produces = "application/hal+json")
public class DoctorController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DoctorService doctorService;

    private final DoctorResourceAssembler doctorResourceAssembler;

    @Autowired
    public DoctorController(DoctorService doctorService,
                            DoctorResourceAssembler doctorResourceAssembler) {
        this.doctorService = doctorService;
        this.doctorResourceAssembler = doctorResourceAssembler;
    }

    @GetMapping("/")
    public Resources<Resource<Doctor>> getAllDoctors() {
        List<Resource<Doctor>> doctors = doctorService.findAllDoctors()
                .stream()
                .map(doctorResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (doctors.isEmpty()) throw new ResourceNotFoundException("Doctors", null, null);
        LOGGER.debug(doctors.toString());
        return new Resources<>(doctors,
                linkTo(methodOn(DoctorController.class).getAllDoctors()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceSupport> getDoctorById(@PathVariable ObjectId id) {
        Doctor doctor = doctorService.findByDoctorId(id);
        if (doctor == null) throw new ResourceNotFoundException("Doctor", "id", id);
        LOGGER.debug("Successful found Doctor:{}", doctor);
        return ResponseEntity.created(linkTo(methodOn(DoctorController.class)
                .getDoctorById(id)).toUri())
                .body(doctorResourceAssembler.toResource(doctor));
    }

    @PostMapping("/")
    public ResponseEntity<ResourceSupport> createDoctor(@RequestBody Doctor doctor) {
        Doctor createDoctor = doctorService.updateDoctor(doctor);
        LOGGER.debug("Successful created Doctor:{}", createDoctor);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createDoctor.getDoctorId()).toUri();
        return ResponseEntity.created(location)
                .body(doctorResourceAssembler.toResource(createDoctor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceSupport> updateDoctor(@PathVariable ObjectId id,
                                                        @RequestBody Doctor doctor) {
        Doctor updateDoctor = doctorService.findByDoctorId(id);
        if (updateDoctor == null) throw new ResourceNotFoundException("Doctor", "id", id);
        if (doctor.getFirstName()!= null) updateDoctor.setFirstName(doctor.getFirstName());
        if (doctor.getLastName() != null) updateDoctor.setLastName(doctor.getLastName());
        if (doctor.getInformation() != null) updateDoctor.setInformation(doctor.getInformation());
        doctorService.updateDoctor(updateDoctor);
        LOGGER.debug("Successful updated Doctor:{}", updateDoctor);
        return ResponseEntity.ok(doctorResourceAssembler.toResource(updateDoctor));
    }
}
