package com.razvan.proiect.services;

import com.razvan.proiect.models.*;
import com.razvan.proiect.repositories.CartRepository;
import com.razvan.proiect.repositories.OrderItemRepository;
import com.razvan.proiect.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void save(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Transactional
    public void placeOrder(User user) {
        Address address = user.getAddress();
        Set<CartItem> cartItems = user.getCartProducts();

        Order order = new Order();
        order.setUser(user);
        order.setDate(new Date());
        order.setCountry(address.getCountry());
        order.setCity(address.getCity());
        order.setLine1(address.getLine1());
        order.setLine2(address.getLine2());
        order.setTotal(cartItems.stream().mapToDouble(i -> i.getQuantity() * i.getProduct().getPrice()).sum());
        Order savedOrder = save(order);

        cartItems.forEach(i -> {
            OrderItem item = new OrderItem();
            item.setName(i.getProduct().getName());
            item.setDescription(i.getProduct().getDescription());
            item.setPrice(i.getProduct().getPrice());
            item.setQuantity(i.getQuantity());
            item.setTotal(i.getProduct().getPrice() * i.getQuantity());
            item.setOrder(savedOrder);
            save(item);
        });

        cartRepository.deleteByUser(user);
    }
}
