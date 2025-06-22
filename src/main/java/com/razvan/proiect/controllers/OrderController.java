package com.razvan.proiect.controllers;

import com.razvan.proiect.models.*;
import com.razvan.proiect.services.CartService;
import com.razvan.proiect.services.OrderService;
import com.razvan.proiect.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public String index(Principal principal, Model model) {
        Optional<User> optionalUser = userService.findUserByUsername(principal.getName());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        List<Order> orders = optionalUser.get().getOrders();
        model.addAttribute("orders", orders);

        return "order-list";
    }

    @GetMapping("/new")
    public String newOrder(Principal principal, Model model) {
        Optional<User> optionalUser = userService.findUserByUsername(principal.getName());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();
        Address address = user.getAddress();
        if (address == null) {
            return "redirect:/address";
        }

        model.addAttribute("address", address);

        Set<CartItem> cartItems = user.getCartProducts();
        model.addAttribute("cartItems", cartItems);

        double total = cartItems.stream().mapToDouble(i -> i.getQuantity() * i.getProduct().getPrice()).sum();
        model.addAttribute("total", total);

        return "new-order";
    }

    @PostMapping("/new")
    public String createOrder(Principal principal) {
        Optional<User> optionalUser = userService.findUserByUsername(principal.getName());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        orderService.placeOrder(optionalUser.get());

        return "redirect:/orders";
    }
}
