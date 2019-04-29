package com.stex.core.api.webapp.controllers.medic;

import com.stex.core.api.medic.models.Medicine;
import com.stex.core.api.medic.services.MedicineService;
import com.stex.core.api.tools.ExceptionHandler.ResourceNotFoundException;
import com.stex.core.api.webapp.ResourcesAssembler.MedicResource.MedicineResourceAssembler;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(value = "/api/medicines", produces = "application/hal+json")
public class MedicineController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MedicineService medicineService;

    private final MedicineResourceAssembler medicineResourceAssembler;

    @Autowired
    public MedicineController(MedicineService medicineService, MedicineResourceAssembler medicineResourceAssembler) {
        this.medicineService = medicineService;
        this.medicineResourceAssembler = medicineResourceAssembler;
    }

    @GetMapping("/")
    public Resources<Resource<Medicine>> getAllMedicines() {
        List<Resource<Medicine>> medicines = medicineService.findAllMedicines()
                .stream()
                .map(medicineResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (medicines.isEmpty()) throw new ResourceNotFoundException("Medicines", null, null);
        LOGGER.debug(medicineService.findAllMedicines().toString());
        return new Resources<>(medicines,
                linkTo(methodOn(MedicineController.class)).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<Medicine>> getMedicineById(@PathVariable ObjectId id) {
        Medicine medicine = medicineService.findByMedicineId(id);
        if (medicine == null) throw new ResourceNotFoundException("Medicine", "id", id);
        LOGGER.debug(medicine.toString());
        return ResponseEntity.created(linkTo(methodOn(MedicineController.class)
                .getMedicineById(id)).toUri())
                .body(medicineResourceAssembler.toResource(medicine));
    }

    @PostMapping("/")
    public ResponseEntity<Resource<Medicine>> createMedicine(@RequestBody Medicine medicine) {
        Medicine createMedicine = medicineService.updateMedicine(medicine);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createMedicine.getMedicineId()).toUri();
        LOGGER.debug("Successful created Medicine:{}", createMedicine);
        return ResponseEntity.created(location)
                .body(medicineResourceAssembler.toResource(createMedicine));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceSupport> updateMedicine(@PathVariable ObjectId id, @RequestBody Medicine medicine) {
        Medicine updateMedicine = medicineService.findByMedicineId(id);
        if (updateMedicine == null) throw new ResourceNotFoundException("Medicine", "id", id);
        if (medicine.getDescription() != null) updateMedicine.setDescription(medicine.getDescription());
        if (medicine.getManufacture() != null) updateMedicine.setManufacture(medicine.getManufacture());
        if (medicine.getName() != null) updateMedicine.setName(medicine.getName());
        updateMedicine.setContent(medicine.getContent());
        updateMedicine.setType(medicine.getType());
        medicineService.updateMedicine(updateMedicine);
        LOGGER.debug("Successful updated Medicine with id: [{}]{}", id, updateMedicine);
        return ResponseEntity.ok(medicineResourceAssembler.toResource(updateMedicine));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resource<Medicine>> deleteMedicine(@PathVariable ObjectId id) {
        Medicine deleteMedicine = medicineService.findByMedicineId(id);
        if (deleteMedicine == null) throw new ResourceNotFoundException("Medicine", "id", id);
        medicineService.deleteMedicine(deleteMedicine);
        LOGGER.debug("Successful deleted Medicine [id: {}]", id);
        return ResponseEntity.ok().build();
    }
}
