package com.stex.core.api.webapp.controllers.medic;

import com.stex.core.api.medic.models.Reception;
import com.stex.core.api.medic.services.ReceptionService;
import com.stex.core.api.tools.ExceptionHandler.ResourceForbiddenException;
import com.stex.core.api.tools.ExceptionHandler.ResourceNotFoundException;
import com.stex.core.api.tools.constants.Status;
import com.stex.core.api.webapp.ResourcesAssembler.MedicResource.ReceptionResourceAssembler;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/receptions")
public class ReceptionContoller {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReceptionService receptionService;

    @Autowired
    private ReceptionResourceAssembler receptionResourceAssembler;

    @GetMapping("/")
    public Resources<Resource<Reception>> getAllReception() {
        List<Resource<Reception>> resources = receptionService.findAllReceptions()
                .stream()
                .map(receptionResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (resources.isEmpty()) throw new ResourceNotFoundException("Receptions", null, null);
        LOGGER.debug(receptionService.findAllReceptions().toString());
        return new Resources<>(resources,
                linkTo(methodOn(ReceptionContoller.class).getAllReception()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceSupport> getReceptionById(@PathVariable ObjectId id) {
        Reception reception = receptionService.findByReceptionId(id);
        if (reception == null) throw new ResourceNotFoundException("Reception", "id", id);
        LOGGER.debug("Successful found Reception:{}", reception);
        return ResponseEntity
                .created(linkTo(methodOn(ReceptionContoller.class).getReceptionById(id)).toUri())
                .body(receptionResourceAssembler.toResource(reception));
    }

    @PostMapping("/")
    public ResponseEntity<ResourceSupport> createReception(@RequestBody Reception reception) {
        reception.setStatus(Status.IN_PROGRESS);
        reception.setCreatedAt(new Date());
        reception.setUpdatedAt(new Date());
        Reception createReception = receptionService.updateReception(reception);
        LOGGER.debug("Successful created Reception:{}", createReception);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createReception.getReceptionId()).toUri();
        return ResponseEntity.created(location)
                .body(receptionResourceAssembler.toResource(createReception));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceSupport> updateReception(@PathVariable ObjectId id
            , @RequestBody Reception reception) {
        Reception updateReception = receptionService.findByReceptionId(id);
        if (updateReception == null) throw new ResourceNotFoundException("Reception", "id", id);
        if (updateReception.getStatus() != Status.IN_PROGRESS) throw new ResourceForbiddenException("Reception", "status", updateReception.getStatus());
        if (reception.getDescription() != null) updateReception.setDescription(reception.getDescription());
        if (reception.getMedicine() != null) updateReception.setMedicine(reception.getMedicine());
        updateReception.setUpdatedAt(new Date());
        receptionService.updateReception(updateReception);
        LOGGER.debug("Successful updated Reception: {}", updateReception);
        return ResponseEntity.ok(receptionResourceAssembler.toResource(reception));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<ResourceSupport> updateStatusReception(@PathVariable ObjectId id
            , @PathVariable String status) {
        Reception reception = receptionService.findByReceptionId(id);
        if (reception == null) throw new ResourceNotFoundException("Reception", "id", id);
        if (reception.getStatus() != Status.IN_PROGRESS)
            throw new ResourceForbiddenException("Reception", "status", reception.getStatus());
        if (status.equals("complete"))
            reception.setStatus(Status.COMPLETED);
        if (status.equals("cancel"))
            reception.setStatus(Status.CANCELLED);
        reception.setUpdatedAt(new Date());
        receptionService.updateReception(reception);
        return ResponseEntity.ok(receptionResourceAssembler.toResource(reception));
    }
}
