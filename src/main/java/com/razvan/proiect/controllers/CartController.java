package com.razvan.proiect.controllers;

import com.razvan.proiect.models.CartItem;
import com.razvan.proiect.models.User;
import com.razvan.proiect.services.CartService;
import com.razvan.proiect.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public String index(Principal principal, Model model) {
        Optional<User> optionalUser = userService.findUserByUsername(principal.getName());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Set<CartItem> cartItems = optionalUser.get().getCartProducts();
        model.addAttribute("cartItems", cartItems);

        double total = cartItems.stream().mapToDouble(i -> i.getQuantity() * i.getProduct().getPrice()).sum();
        model.addAttribute("total", total);

        return "cart";
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable String id, Principal principal) {
        Optional<User> optionalUser = userService.findUserByUsername(principal.getName());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        cartService.add(optionalUser.get(), Long.parseLong(id));

        return "redirect:/cart";
    }

    @GetMapping("/decrease/{id}")
    public String decrease(@PathVariable String id, Principal principal) {
        Optional<User> optionalUser = userService.findUserByUsername(principal.getName());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        cartService.decrease(optionalUser.get(), Long.parseLong(id));

        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable String id, Principal principal) {
        Optional<User> optionalUser = userService.findUserByUsername(principal.getName());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        cartService.remove(optionalUser.get(), Long.parseLong(id));

        return "redirect:/cart";
    }
}
