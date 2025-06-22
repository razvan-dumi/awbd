package com.razvan.proiect.repositories;

import com.razvan.proiect.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
