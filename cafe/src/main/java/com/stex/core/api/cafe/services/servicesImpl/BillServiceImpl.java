package com.stex.core.api.cafe.services.servicesImpl;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.cafe.repositories.BillRepository;
import com.stex.core.api.cafe.services.BillService;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    public Bill findByBillId(ObjectId id) {
        return billRepository.findById(id);
    }

    public List<Bill> findAllBills() {
        return billRepository.findAll();
    }

    public List<Bill> findAllByBillStatus(Status status) {
        return billRepository.findAllByStatus(status);
    }

    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    public Bill updateBill(Bill bill) {
        return billRepository.save(bill);
    }
}
