package com.stex.core.api.cafe.services.servicesImpl;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.cafe.repositories.BillRepository;
import com.stex.core.api.cafe.services.BillService;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill findByBillId(ObjectId id) {
        return billRepository.findById(id);
    }

    public List<Bill> findAllBills() {
        return billRepository.findAll();
    }

    public List<Bill> findAllByBillStatus(Status status) {
        return billRepository.findAllByStatus(status);
    }

    @Override
    public boolean isAvailableTable(Status status, int table) {
        List<Bill> bills = billRepository.findBillByStatusAndTable(status, table);
        return bills.isEmpty();
    }

    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    public Bill updateBill(Bill bill) {
        return billRepository.save(bill);
    }
}
