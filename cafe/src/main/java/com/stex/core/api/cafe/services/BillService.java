package com.stex.core.api.cafe.services;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.cafe.models.Order;
import com.stex.core.api.cafe.repositories.BillRepository;
import com.stex.core.api.tools.ExceptionHandler.ResourceNotFoundException;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BillService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BillRepository billRepository;

    public Bill getBill(ObjectId id) {
        try {
            Bill foundBill = billRepository.findById(id);
            LOGGER.info("Successful found Bill {}", foundBill.toString());
            return foundBill;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Bill", "id", id);
        }
    }

    public List<Bill> getAllBills() {
        LOGGER.info("Loading all bills ...");
        return billRepository.findAll();
    }

    public void createBill(Bill bill) {
        bill.setCreatedAt(new Date());
        bill.setUpdatedAt(new Date());
        billRepository.save(bill);
        LOGGER.info("Successful created bill {}", bill.toString());
    }

    /**
     *
     * @param bill update Bill
     * @return Http.Status
     */
    public HttpStatus updateBill(Bill bill) {
        try {
            Bill updateBill = billRepository.findById(bill.getId());
            if (updateBill.getStatus() != Status.IN_PROGRESS) {
                LOGGER.debug("Method not allowed." +
                        "You cannot update bill that is in the {} status.", updateBill.getStatus());
                return HttpStatus.METHOD_NOT_ALLOWED;
            }
            updateBill.setOrders(bill.getOrders());
            updateBill.setTable(bill.getTable());
            updateBill.setUpdatedAt(new Date());
            billRepository.save(updateBill);
            LOGGER.info("Successful updated Bill {}", updateBill.toString());
            return HttpStatus.ACCEPTED;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Bill", "id", bill.getId());
        }
    }

    public HttpStatus checkout(Bill bill) {
        try {
            Bill checkoutBill = billRepository.findById(bill.getId());
            if (checkoutBill.getStatus() != Status.IN_PROGRESS) {
                LOGGER.debug("Method not allowed." +
                        "You cannot checkout bill that is in the {} status.", checkoutBill.getStatus());
                return HttpStatus.METHOD_NOT_ALLOWED;
            }
            checkoutBill.setUpdatedAt(new Date());
            double preis = 0;
            for (Order order : bill.getOrders()) {
                preis += order.getProduct().getPreis() * order.getQuantity();
            }
            checkoutBill.setPreis(preis);
            billRepository.save(checkoutBill);
            LOGGER.info("Successful check out Bill {}", checkoutBill.toString());
            return HttpStatus.ACCEPTED;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Bill", "id", bill.getId());
        }

    }
}
