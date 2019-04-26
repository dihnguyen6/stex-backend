package com.stex.core.api.webapp;

import com.stex.core.api.cafe.services.BillService;
import com.stex.core.api.cafe.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan({"com.stex.core.*"})
@EnableMongoRepositories({"com.stex.core.*"})
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    OrderService orderService;
    @Autowired
    BillService billService;
    @Override
    public void run(String... args) throws Exception {
        /*Order order = orderService.findByOrderId(new ObjectId("5cc2c496eeec5135e822abe2"));
        Bill bill = new Bill();
        bill.setUpdatedAt(new Date());
        bill.setCreatedAt(new Date());
        bill.setPreis(0);
        bill.setStatus(Status.IN_PROGRESS);
        bill.setTable(1);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        bill.setOrders(orders);
        billService.createBill(bill);*/
    }
}
