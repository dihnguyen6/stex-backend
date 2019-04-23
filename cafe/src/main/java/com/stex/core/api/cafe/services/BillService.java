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

    Bill createBill(Bill bill);
    void updateBill(Bill bill);
    void checkoutBill(ObjectId id);
    void cancelBill(ObjectId id);

}
