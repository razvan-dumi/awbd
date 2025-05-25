package com.razvan.proiect.services;

import com.razvan.proiect.models.Product;
import com.razvan.proiect.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void save(Product product) {
        productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> paginate(int page, int pageSize) {
        return productRepository.findAll(Pageable.ofSize(pageSize).withPage(page)).getContent();
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
