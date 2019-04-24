package com.stex.core.api.cafe.services.servicesImpl;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.cafe.models.Order;
import com.stex.core.api.cafe.repositories.BillRepository;
import com.stex.core.api.cafe.services.BillService;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BillRepository billRepository;

    public Bill findByBillId(ObjectId id) {
        if (billRepository.existsById(String.valueOf(id))) {
            Bill foundBill = billRepository.findById(id);
            LOGGER.info("Successful found Bill {}", foundBill);
            return foundBill;
        } else {
            LOGGER.info("Cannot found Bill with id: [{}]", id);
            return null;
        }
    }

    public List<Bill> findAllBills() {
        List<Bill> bills = billRepository.findAll();
        if (bills.isEmpty()) {
            LOGGER.info("Bill's list is empty.");
            return null;
        } else {
            LOGGER.info("Loading all bills ...");
            for (Bill bill : bills) {
                LOGGER.info("\nBill: {}\n", bill);
            }
            return bills;
        }

    }

    public List<Bill> findAllByBillStatus(Status status) {
        List<Bill> bills = billRepository.findAllByStatus(status);
        if (bills.isEmpty()) {
            LOGGER.info("Bill's list is empty.");
            return null;
        } else {
            LOGGER.info("Loading all bills that are in the {} status.", status);
            for (Bill bill : bills) {
                LOGGER.info("\nBill: [{}]\n", bill);
            }
            return bills;
        }

    }

    public Bill findByBillStatusAndTable(Status status, int table) {
        if (billRepository.findByStatusAndTable(status, table) != null) {
            LOGGER.info("Successful found bill that is in {} status and {} table.", status, table);
            LOGGER.info("\nBill: {}\n", billRepository.findByStatusAndTable(status, table));
            return billRepository.findByStatusAndTable(status, table);
        } else {
            LOGGER.info("Cannot found bill that is in {} status and {} table.", status, table);
            return null;
        }
    }

    public Bill createBill(Bill bill) {
        bill.setCreatedAt(new Date());
        bill.setUpdatedAt(new Date());
        bill.setPreis(0);
        bill.setStatus(Status.IN_PROGRESS);
        Bill createBill = billRepository.save(bill);
        LOGGER.info("Successful created bill {}", createBill.toString());
        return createBill;
    }

    /**
     * @param bill updateBill
     */
    public Bill updateBill(Bill bill) {
        if (billRepository.existsById(String.valueOf(bill.getId()))) {
            Bill updateBill = billRepository.findById(bill.getId());
            updateBill.setOrders(bill.getOrders());
            updateBill.setTable(bill.getTable());
            updateBill.setUpdatedAt(new Date());
            billRepository.save(updateBill);
            LOGGER.info("Successful updated Bill {}", updateBill.toString());
            return updateBill;
        } else {
            LOGGER.info("Cannot found bill with id: [{}]", bill.getId());
            return null;
        }
    }

    public Bill checkoutBill(ObjectId id) {
        if (billRepository.existsById(String.valueOf(id))) {
            Bill checkoutBill = billRepository.findById(id);
            checkoutBill.setUpdatedAt(new Date());
            double preis = 0;
            for (Order order : checkoutBill.getOrders()) {
                preis += order.getProduct().getPreis() * order.getQuantity();
            }
            checkoutBill.setPreis(preis);
            checkoutBill.setStatus(Status.COMPLETED);
            billRepository.save(checkoutBill);
            LOGGER.info("Successful check out Bill {}", checkoutBill.toString());
            return checkoutBill;
        } else {
            LOGGER.info("Cannot found bill with id: [{}]", id);
            return null;
        }
    }

    public Bill cancelBill(ObjectId id) {
        if (billRepository.existsById(String.valueOf(id))) {
            Bill cancelBill = billRepository.findById(id);
            cancelBill.setStatus(Status.CANCELLED);
            cancelBill.setUpdatedAt(new Date());
            LOGGER.info("Successful cancelled Bill {}", cancelBill.toString());
            return cancelBill;
        } else {
            LOGGER.info("Cannot found bill with id: [{}]", id);
            return null;
        }
    }
}
