package com.razvan.proiect.service;

import com.razvan.proiect.models.Product;
import com.razvan.proiect.repositories.ProductRepository;
import com.razvan.proiect.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

public class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService();

        Field field = ProductService.class.getDeclaredField("productRepository");
        field.setAccessible(true);
        field.set(productService, productRepository);
    }
    
    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setName("test");
        product.setDescription("test");
        product.setPrice(30.0);

        productService.save(product);

        Mockito.verify(productRepository, Mockito.times(1)).save(product);
    }
}
