package com.stex.core.api.webapp;

import com.stex.core.api.cafe.models.Category;
import com.stex.core.api.cafe.models.Product;
import com.stex.core.api.cafe.services.CategoryService;
import com.stex.core.api.cafe.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan({"com.stex.core.*"})
@EnableMongoRepositories({"com.stex.core.*"})
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initDatabase();
    }

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    public void initDatabase() {
        List<Category> categories = new ArrayList<>();
        if (categoryService.findAllCategories().isEmpty()) {
            categories.add(new Category("Trà Hoa Cúc"));
            categories.add(new Category("Trà Cung Đình Huế"));
            categories.add(new Category("Cà Phê"));
            categories.add(new Category("Đồ Uống"));
            categories.add(new Category("Hoa Quả"));
            categories.add(new Category("Nước Ép"));
            categories.add(new Category("Sinh Tố"));
            categories.add(new Category("Món Ăn Nhẹ"));
            categoryService.importCategories(categories);
        }

        List<Product> products = new ArrayList<>();
        if (productService.findAllProducts().isEmpty()) {
            products.add(new Product("Hồng Trà Cam Quế", 35000, categories.get(0)));
            products.add(new Product("Trà Cam Sả", 30000, categories.get(0)));
            products.add(new Product("Trà Chanh Gừng Mật Ong", 30000, categories.get(0)));
            products.add(new Product("Trà Dilmah Dâu", 25000, categories.get(0)));
            products.add(new Product("Trà Dilmah Đào", 25000, categories.get(0)));
            products.add(new Product("Trà Dilmah Bạc Hà", 25000, categories.get(0)));

            products.add(new Product("Cà Phê Phin Đen", 20000, categories.get(2)));
            products.add(new Product("Cà Phê Phin Nâu", 20000, categories.get(2)));
            products.add(new Product("Cà Phê Đen", 20000, categories.get(2)));
            products.add(new Product("Cà Phê Nâu", 20000, categories.get(2)));
            products.add(new Product("Cà Phê Trứng", 35000, categories.get(2)));
            products.add(new Product("Cacao Trứng", 40000, categories.get(2)));
            products.add(new Product("Bạc Xỉu", 25000, categories.get(2)));
            products.add(new Product("Sữa Chua Cà Phê", 25000, categories.get(2)));
            products.add(new Product("Cacao Nóng", 30000, categories.get(2)));
            products.add(new Product("Cacao Đá", 30000, categories.get(2)));

            products.add(new Product("Bột Sắn Đá", 30000, categories.get(3)));
            products.add(new Product("Bột Sắn Sệt", 30000, categories.get(3)));
            products.add(new Product("Bột Sắn", 40000, categories.get(3)));
            products.add(new Product("Matcha Sữa", 30000, categories.get(3)));

            products.add(new Product("Hoa Quả Dầm Thập Cẩm", 40000, categories.get(4)));
            products.add(new Product("Mãng Cầu Dầm", 40000, categories.get(4)));
            products.add(new Product("Bơ Dầm", 40000, categories.get(4)));
            products.add(new Product("Na Dầm", 40000, categories.get(4)));
            products.add(new Product("Mít Dầm", 35000, categories.get(4)));
            products.add(new Product("Mít Dầm Sữa Chua", 35000, categories.get(4)));
            products.add(new Product("Măng Cụt Dầm", 40000, categories.get(4)));
            products.add(new Product("Xoài Dầm", 40000, categories.get(4)));
            products.add(new Product("Vú Sữa Dầm", 40000, categories.get(4)));
            products.add(new Product("Dâu Tây Dầm", 45000, categories.get(4)));
            products.add(new Product("Sữa Chua Dầm Thạch", 80000, categories.get(4)));
            products.add(new Product("Sữa Chua Nếp Cẩm", 80000, categories.get(4)));

            products.add(new Product("Bưởi", 40000, categories.get(5)));
            products.add(new Product("Bưởi Mật Ong", 45000, categories.get(5)));
            products.add(new Product("Cam", 40000, categories.get(5)));
            products.add(new Product("Cam Mật Ong", 45000, categories.get(5)));
            products.add(new Product("Chanh Leo", 30000, categories.get(5)));
            products.add(new Product("Táo", 35000, categories.get(5)));
            products.add(new Product("Lựu", 35000, categories.get(5)));
            products.add(new Product("Lê", 35000, categories.get(5)));
            products.add(new Product("Chanh Tươi", 25000, categories.get(5)));
            products.add(new Product("Dưa Hấu", 30000, categories.get(5)));
            products.add(new Product("Dưa Vàng", 30000, categories.get(5)));
            products.add(new Product("Ổi", 25000, categories.get(5)));
            products.add(new Product("Cóc", 30000, categories.get(5)));
            products.add(new Product("Bí Ngòi", 30000, categories.get(5)));
            products.add(new Product("Dứa", 30000, categories.get(5)));
            products.add(new Product("Rau Má", 20000, categories.get(5)));

            products.add(new Product("Mãng Cầu", 40000, categories.get(6)));
            products.add(new Product("Chanh Leo", 35000, categories.get(6)));
            products.add(new Product("Xoài", 40000, categories.get(6)));
            products.add(new Product("Dưa Hấu", 35000, categories.get(6)));
            products.add(new Product("Đu Đủ", 35000, categories.get(6)));
            products.add(new Product("Chanh Tuyết", 30000, categories.get(6)));
            products.add(new Product("Sữa Chua", 30000, categories.get(6)));
            products.add(new Product("Bơ", 40000, categories.get(6)));
            products.add(new Product("Cam", 40000, categories.get(6)));
            products.add(new Product("Sầu Riêng", 50000, categories.get(6)));
            products.add(new Product("Mít", 40000, categories.get(6)));
            productService.importProduct(products);
        }
    }
}
