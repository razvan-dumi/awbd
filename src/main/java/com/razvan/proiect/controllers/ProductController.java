package com.razvan.proiect.controllers;

import com.razvan.proiect.models.Product;
import com.razvan.proiect.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String listProducts(Model model) {
        List<Product> products = productService.paginate(0, 20);
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        Optional<Product> optionalProduct = productService.getProductById(Long.parseLong(id));
        optionalProduct.ifPresent(product -> model.addAttribute("product", product));
        return "product-form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.delete(Long.parseLong(id));
        return "redirect:/products";
    }
}
