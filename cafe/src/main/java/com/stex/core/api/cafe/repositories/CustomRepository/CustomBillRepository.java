package com.stex.core.api.cafe.repositories.CustomRepository;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.tools.Status;

import java.util.List;

public interface CustomBillRepository {
    List<Bill> findBillByStatusAndTable(Status status, int table);
}
