package com.stex.core.api.webapp.controllers.cafe;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.cafe.models.Order;
import com.stex.core.api.cafe.services.BillService;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BillService billService;

    @GetMapping("/")
    public HttpEntity<List<Bill>> getAllBills() {
        List<Bill> bills = billService.findAllBills();
        if (bills.isEmpty()) {
            LOGGER.debug("Bill's list is empty.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            addHateoasToList(bills);
            LOGGER.debug("Successful found Bills: {}", bills);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public HttpEntity<Bill> getBillById(@PathVariable ObjectId id) {
        Bill bill = billService.findByBillId(id);
        if (bill == null) {
            LOGGER.debug("Cannot found Bill [id: {}]", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            addHateoasToBill(bill);
            LOGGER.debug("Successful found Bill: {}", bill);
            return new ResponseEntity<>(bill, HttpStatus.OK);
        }
    }

    @GetMapping("/progress")
    public HttpEntity<List<Bill>> getBillsInProgress() {
        List<Bill> bills = billService.findAllByBillStatus(Status.IN_PROGRESS);
        if (bills.isEmpty()) {
            LOGGER.debug("List of bills that are in the {} status, is empty.", Status.IN_PROGRESS);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            addHateoasToList(bills);
            LOGGER.debug("Successful found {} bills: {}", Status.IN_PROGRESS, bills);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        }
    }

    @GetMapping("/completed")
    public HttpEntity<List<Bill>> getBillsCompleted() {
        List<Bill> bills = billService.findAllByBillStatus(Status.COMPLETED);
        if (bills.isEmpty()) {
            LOGGER.debug("List of bills that are in the {} status, is empty.", Status.COMPLETED);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            addHateoasToList(bills);
            LOGGER.debug("Successful found {} bills: {}", Status.COMPLETED, bills);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        }
    }

    @GetMapping("/cancelled")
    public HttpEntity<List<Bill>> getBillsCancel() {
        List<Bill> bills = billService.findAllByBillStatus(Status.CANCELLED);
        if (bills.isEmpty()) {
            LOGGER.debug("List of bills that are in the {} status, is empty.", Status.CANCELLED);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            addHateoasToList(bills);
            LOGGER.debug("Successful found {} bills: {}", Status.CANCELLED, bills);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public HttpEntity<Bill> createBill(@RequestBody Bill bill) {
        if (!validateAvailableTable(bill.getTable())) {
            LOGGER.debug("Table [{}] is not available now." +
                    " Checkout then try again.", bill.getTable());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            bill.setUpdatedAt(new Date());
            bill.setCreatedAt(new Date());
            bill.setStatus(Status.IN_PROGRESS);
            //bill.setOrders(new ArrayList<>());
            Bill createBill = billService.createBill(bill);
            addHateoasToBill(createBill);
            LOGGER.debug("Successful created Bill: {}", createBill);
            return new ResponseEntity<>(createBill, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public HttpEntity<Bill> addOrderToBill(@PathVariable ObjectId id, @RequestBody Bill bill) {
        Bill updateBill = billService.findByBillId(id);
        if (updateBill == null) {
            LOGGER.debug("Cannot found Bill with [id: {}]", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            updateBill.getOrders().addAll(bill.getOrders());
            billService.updateBill(updateBill);
            addHateoasToBill(updateBill);
            LOGGER.debug("Successful updated Bill: {}", updateBill);
            return new ResponseEntity<>(updateBill, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}?changTable={table}")
    public HttpEntity<Bill> changeTable(@PathVariable ObjectId id, @PathVariable int table) {
        Bill updateBill = billService.findByBillId(id);
        if (updateBill == null) {
            LOGGER.debug("Cannot found Bill [id: {}]", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            updateBill.setUpdatedAt(new Date());
            updateBill.setTable(table);
            billService.updateBill(updateBill);
            addHateoasToBill(updateBill);
            LOGGER.debug("Successful change Table: {}", updateBill);
            return new ResponseEntity<>(updateBill, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}?checkout")
    public HttpEntity<Bill> checkoutBill(@PathVariable ObjectId id) {
        Bill checkoutBill = billService.findByBillId(id);
        if (checkoutBill == null) {
            LOGGER.debug("Cannot found Bill with [id: {}]", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (checkoutBill.getStatus() != Status.IN_PROGRESS) {
                LOGGER.debug("Method is not allowed." +
                        " Cannot checkout Bill that is in the {} status.", checkoutBill.getStatus());
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                checkoutBill.setStatus(Status.COMPLETED);
                double preis = 0;
                for (Order order: checkoutBill.getOrders()) {
                    preis += order.getQuantity() * order.getProduct().getPreis();
                }
                checkoutBill.setPreis(preis);
                checkoutBill.setUpdatedAt(new Date());
                billService.updateBill(checkoutBill);
                addHateoasToBill(checkoutBill);
                LOGGER.debug("Successful checkout Bill: {}", checkoutBill);
                return new ResponseEntity<>(checkoutBill, HttpStatus.OK);
            }
        }
    }

    @PutMapping("/{id}?cancel")
    public HttpEntity<Bill> cancelBill(@PathVariable ObjectId id) {
        Bill cancelBill = billService.findByBillId(id);
        if (cancelBill == null) {
            LOGGER.debug("Cannot found Bill with [id: {}]", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (cancelBill.getStatus() != Status.IN_PROGRESS) {
                LOGGER.debug("Method is not allowed." +
                        " Cannot cancel Bill that is in the {} status.", cancelBill.getStatus());
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                cancelBill.setUpdatedAt(new Date());
                cancelBill.setStatus(Status.CANCELLED);
                billService.updateBill(cancelBill);
                addHateoasToBill(cancelBill);
                LOGGER.debug("Successful cancel Bill: {}", cancelBill);
                return new ResponseEntity<>(cancelBill, HttpStatus.OK);
            }
        }
    }

    private boolean validateAvailableTable(int table) {
        List<Bill> bills = billService.findAllByBillStatus(Status.IN_PROGRESS);
        //TODO change Algorithmus to avoid hit the performance
        for (Bill bill : bills) {
            if (bill.getTable() == table) return false;
        }
        return true;
    }

    private void addHateoasToList(List<Bill> bills) {
        bills.forEach(b -> b.add(linkTo(methodOn(BillController.class)
                .getAllBills())
                .withRel("bills")));
        bills.forEach(b -> b.add(linkTo(methodOn(BillController.class)
                .getBillById(b.getBillId()))
                .withSelfRel()));
    }

    private void addHateoasToBill(Bill bill) {
        bill.add(linkTo(methodOn(BillController.class)
                .getAllBills())
                .withRel("bills"));
        bill.add(linkTo(methodOn(BillController.class)
                .getBillById(bill.getBillId()))
                .withSelfRel());
        if (bill.getOrders() == null) {
            bill.setOrders(new ArrayList<>());
        }
        bill.getOrders()
                .forEach(o -> o.add(linkTo(methodOn(OrderController.class)
                        .getOrderById(o.getOrderId())).withSelfRel()));
    }
}
