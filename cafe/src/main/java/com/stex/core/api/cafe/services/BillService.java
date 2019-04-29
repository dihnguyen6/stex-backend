package com.stex.core.api.cafe.services;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.tools.constants.Status;
import org.bson.types.ObjectId;

import java.util.List;

public interface BillService {
    Bill findByBillId(ObjectId id);
    List<Bill> findAllBills();
    List<Bill> findAllByBillStatus(Status status);
    boolean isAvailableTable(Status status, int table);
    Bill updateBill(Bill bill);
}
