package com.razvan.proiect.repositories;

import com.razvan.proiect.models.CartItem;
import com.razvan.proiect.models.CartItemKey;
import com.razvan.proiect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem, CartItemKey> {
    void deleteByUser(User user);
}
