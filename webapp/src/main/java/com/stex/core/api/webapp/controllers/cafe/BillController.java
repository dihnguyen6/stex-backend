package com.stex.core.api.webapp.controllers.cafe;

import com.stex.core.api.cafe.models.Bill;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return new ResponseEntity<>(bill, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public HttpEntity<Bill> createBill(@RequestBody Bill bill) {
        if (validateAvailableTable(Status.IN_PROGRESS, bill.getTable())) {
            LOGGER.debug("Table [{}] is not available now." +
                    "Checkout then try again.", bill.getTable());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            bill.setUpdatedAt(new Date());
            bill.setCreatedAt(new Date());
            bill.setStatus(Status.IN_PROGRESS);
            billService.createBill(bill);
            addHateoasToBill(bill);
            return new ResponseEntity<>(bill, HttpStatus.OK);
        }
    }

    private boolean validateAvailableTable(Status status, int table) {
        return billService.existsBill(billService.findByBillStatusAndTable(status, table));
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
        bill.getOrders()
                .forEach(o -> o.add(linkTo(methodOn(OrderController.class)
                        .getOrderById(o.getOrderId())).withSelfRel()));
    }
}
