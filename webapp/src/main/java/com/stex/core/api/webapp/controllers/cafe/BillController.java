package com.stex.core.api.webapp.controllers.cafe;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.cafe.models.Order;
import com.stex.core.api.cafe.services.BillService;
import com.stex.core.api.cafe.services.OrderService;
import com.stex.core.api.tools.ExceptionHandler.ResourceForbiddenException;
import com.stex.core.api.tools.ExceptionHandler.ResourceNotFoundException;
import com.stex.core.api.tools.ExceptionHandler.ResourceTableNotAvailableException;
import com.stex.core.api.tools.constants.Status;
import com.stex.core.api.webapp.ResourcesAssembler.CafeResource.BillResourceAssembler;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = "/api/bills", produces = "application/hal+json")
public class BillController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final BillService billService;

    private final OrderService orderService;

    private final BillResourceAssembler billResourceAssembler;

    @Autowired
    public BillController(BillService billService, OrderService orderService,
                          BillResourceAssembler billResourceAssembler) {
        this.billService = billService;
        this.orderService = orderService;
        this.billResourceAssembler = billResourceAssembler;
    }

    @GetMapping("/")
    public Resources<Resource<Bill>> getAllBills() {
        List<Resource<Bill>> bills = billService.findAllBills()
                .stream()
                .map(billResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (bills.isEmpty())
            throw new ResourceNotFoundException("Bills", null, null);
        LOGGER.debug(billService.findAllBills().toString());
        return new Resources<>(bills,
                linkTo(methodOn(BillController.class).getAllBills()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<Bill>> getBillById(@PathVariable ObjectId id) {
        Bill bill = billService.findByBillId(id);
        if (bill == null)
            throw new ResourceNotFoundException("Bill", "id", id);
        LOGGER.debug(bill.toString());
        return ResponseEntity.created(linkTo(methodOn(BillController.class)
                .getBillById(id)).toUri())
                .body(billResourceAssembler.toResource(bill));
    }

    @GetMapping("/progress")
    public Resources<Resource<Bill>> getBillsInProgress() {
        List<Resource<Bill>> bills = billService.findAllByBillStatus(Status.IN_PROGRESS)
                .stream()
                .map(billResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (bills.isEmpty())
            throw new ResourceNotFoundException("Bills", "status", Status.IN_PROGRESS);
        LOGGER.debug(billService.findAllByBillStatus(Status.IN_PROGRESS).toString());
        return new Resources<>(bills,
                linkTo(methodOn(BillController.class).getBillsInProgress()).withSelfRel());
    }

    @GetMapping("/completed")
    public Resources<Resource<Bill>> getBillsCompleted() {
        List<Resource<Bill>> bills = billService.findAllByBillStatus(Status.COMPLETED)
                .stream()
                .map(billResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (bills.isEmpty())
            throw new ResourceNotFoundException("Bills", "status", Status.COMPLETED);
        LOGGER.debug(billService.findAllByBillStatus(Status.COMPLETED).toString());
        return new Resources<>(bills,
                linkTo(methodOn(BillController.class)).withSelfRel());
    }

    @GetMapping("/cancelled")
    public Resources<Resource<Bill>> getBillsCancel() {
        List<Resource<Bill>> bills = billService.findAllByBillStatus(Status.CANCELLED)
                .stream()
                .map(billResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (bills.isEmpty())
            throw new ResourceNotFoundException("Bills", "status", Status.CANCELLED);
        LOGGER.debug(billService.findAllByBillStatus(Status.CANCELLED).toString());
        return new Resources<>(bills,
                linkTo(methodOn(BillController.class)).withSelfRel());
    }

    @PostMapping("/")
    public ResponseEntity<Resource<Bill>> createBill(@RequestBody Bill bill) {
        if (isNotAvailableTable(bill.getTable()))
            throw new ResourceForbiddenException("Bill", "table", bill.getTable());
        bill.setUpdatedAt(new Date());
        bill.setCreatedAt(new Date());
        bill.setStatus(Status.IN_PROGRESS);
        bill.setOrders(new ArrayList<>());
        Bill createBill = billService.updateBill(bill);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createBill.getBillId()).toUri();
        LOGGER.debug("Successful created Bill: {}", createBill);
        return ResponseEntity.created(location)
                .body(billResourceAssembler.toResource(createBill));

    }

    @PutMapping("/{id}/add/{orderId}")
    public ResponseEntity<ResourceSupport> addOrderToBill(@PathVariable ObjectId id,
                                                          @PathVariable ObjectId orderId) {
        Bill updateBill = billService.findByBillId(id);
        if (updateBill == null)
            throw new ResourceNotFoundException("Bill", "id", id);
        Order createOrder = orderService.findByOrderId(orderId);
        List<Order> orders = updateBill.getOrders();
        orders.add(createOrder);
        updateBill.setOrders(orders);
        updateBill.setUpdatedAt(new Date());
        billService.updateBill(updateBill);
        LOGGER.debug("Successful updated Bill: {}", updateBill);
        return ResponseEntity.ok(billResourceAssembler.toResource(updateBill));
    }

    @PutMapping("/{id}/change/{table}")
    public ResponseEntity<Resource<Bill>> changeTable(@PathVariable ObjectId id,
                                                      @PathVariable int table) {
        Bill updateBill = billService.findByBillId(id);
        if (updateBill == null)
            throw new ResourceNotFoundException("Bill", "id", id);
        if (isNotAvailableToUpdate(updateBill))
            throw new ResourceTableNotAvailableException("Bill", "status", updateBill.getStatus());
        if (isNotAvailableTable(table))
            throw new ResourceForbiddenException("Bill", "table", table);
        updateBill.setUpdatedAt(new Date());
        updateBill.setTable(table);
        billService.updateBill(updateBill);
        LOGGER.debug("Successful change Bill [{}] to Table: {}", updateBill.getBillId(), table);
        return ResponseEntity.ok(billResourceAssembler.toResource(updateBill));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<ResourceSupport> updateStatusBill(@PathVariable ObjectId id,
                                                            @PathVariable String status) {
        Bill bill = billService.findByBillId(id);
        if (bill == null)
            throw new ResourceNotFoundException("Bill", "id", id);
        if (isNotAvailableToUpdate(bill))
            throw new ResourceForbiddenException("Bill", "status", bill.getStatus());
        bill.setUpdatedAt(new Date());
        if (status.equals("complete")) {
            double preis = 0;
            for (Order order : bill.getOrders()) {
                preis += order.getQuantity() * order.getProduct().getPreis();
            }
            bill.setPreis(preis);
            bill.setStatus(Status.COMPLETED);
        } else if (status.equals("cancel")) {
            bill.setStatus(Status.CANCELLED);
        }
        LOGGER.debug(bill.toString());
        billService.updateBill(bill);
        return ResponseEntity.ok(billResourceAssembler.toResource(bill));
    }

    private boolean isNotAvailableToUpdate(Bill bill) {
        return bill.getStatus() != Status.IN_PROGRESS;
    }

    private boolean isNotAvailableTable(int table) {
        return !billService.isAvailableTable(Status.IN_PROGRESS, table);
    }
}
