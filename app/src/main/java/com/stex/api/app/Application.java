package com.stex.api.app;

import com.stex.core.api.cafe.CafeService;
import com.stex.core.api.cafe.services.ProductService;
import com.stex.core.api.medic.MedicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.stex.core.api.cafe", "com.stex.core.api.medic","com.stex.core.api.tools"})
//@EnableMongoRepositories(basePackages = {"com.stex.core.api.cafe.repositories", "com.stex.core.api.medic.repositories"})
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        new SpringApplicationBuilder(CafeService.class, MedicService.class).run(args);
    }

    @Autowired
    ProductService service;

    public void run(String... args) throws Exception {
        /*service.createProduct(new Product("Tra Cuc", 25000));
        service.createProduct(new Product("Tra Cuc1", 50000));
        service.createProduct(new Product("Tra Cuc2", 35000));
        service.findAllProducts();*/
    }
}
