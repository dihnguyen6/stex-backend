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
        Bill foundBill = billRepository.findById(id);
        LOGGER.info("Successful found Bill {}", foundBill.toString());
        return foundBill;
    }

    public List<Bill> findAllBills() {
        LOGGER.info("Loading all bills ...");
        return billRepository.findAll();
    }

    public List<Bill> findAllByBillStatus(Status status) {
        LOGGER.info("Loading all bills that are in the {} status.", status);
        return billRepository.findAllByStatus(status);
    }

    public Bill findByBillStatusAndTable(Status status, int table) {
        LOGGER.info("Successful found bill that is in {} status and {} table.", status, table );
        return billRepository.findByStatusAndTable(status, table);
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
    public void updateBill(Bill bill) {
        Bill updateBill = billRepository.findById(bill.getBillId());
        updateBill.setOrders(bill.getOrders());
        updateBill.setTable(bill.getTable());
        updateBill.setUpdatedAt(new Date());
        billRepository.save(updateBill);
        LOGGER.info("Successful updated Bill {}", updateBill.toString());
    }

    public void checkoutBill(ObjectId id) {
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
    }

    public void cancelBill(ObjectId id) {
        Bill cancelBill = billRepository.findById(id);
        cancelBill.setStatus(Status.CANCELLED);
        cancelBill.setUpdatedAt(new Date());
        LOGGER.info("Successful cancelled Bill {}", cancelBill.toString());
    }
}
