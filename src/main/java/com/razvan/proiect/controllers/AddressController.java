package com.razvan.proiect.controllers;

import com.razvan.proiect.models.Address;
import com.razvan.proiect.models.User;
import com.razvan.proiect.services.AddressService;
import com.razvan.proiect.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @GetMapping("")
    public String addressForm(Principal principal, Model model) {
        Optional<User> optionalUser = userService.findUserByUsername(principal.getName());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Address address = optionalUser.get().getAddress();
        if (address == null) {
            address = new Address();
        }
        model.addAttribute("address", address);
        return "address-form";
    }

    @PostMapping("")
    public String saveAddress(Principal principal, @ModelAttribute("address") Address address) {
        Optional<User> optionalUser = userService.findUserByUsername(principal.getName());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        address.setUser(optionalUser.get());
        addressService.save(address);
        return "redirect:/address";
    }
}
