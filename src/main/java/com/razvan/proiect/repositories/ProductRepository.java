package com.razvan.proiect.repositories;

import com.razvan.proiect.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
