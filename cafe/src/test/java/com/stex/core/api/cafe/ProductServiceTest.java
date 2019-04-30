/*
package com.stex.core.api.cafe;

import com.stex.core.api.cafe.models.Product;
import com.stex.core.api.cafe.repositories.ProductRepository;
import com.stex.core.api.cafe.services.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@DataMongoTest
public class ProductServiceTest {

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    private List<Product> productList = new ArrayList<>();

    @Test
    public void contextLoads() throws Exception {
        assertThat(productService).isNotNull();
        assertThat(productRepository).isNotNull();
    }

    @Test
    public void createProducts() {
        Product product = new Product();
        product.setName("Trà Chanh");
        product.setPreis(25000);
        productList.add(product);

        when(productRepository.save(product)).thenReturn(product);
        when(productService.updateProduct(product)).thenReturn(product);
        verify(productRepository, times(1)).save(product);
        verify(productService, times(1)).updateProduct(product);
    }

}
*/
