package com.stex.core.api.cafe.services;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;

import java.util.List;

public interface BillService {
    Bill findByBillId(ObjectId id);
    List<Bill> findAllBills();
    List<Bill> findAllByBillStatus(Status status);
    Bill findByBillStatusAndTable(Status status, int table);
    boolean existsBill(Bill bill);
    Bill createBill(Bill bill);
    Bill updateBill(Bill bill);
}
