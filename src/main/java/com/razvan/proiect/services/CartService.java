package com.razvan.proiect.services;

import com.razvan.proiect.models.CartItem;
import com.razvan.proiect.models.CartItemKey;
import com.razvan.proiect.models.Product;
import com.razvan.proiect.models.User;
import com.razvan.proiect.repositories.CartRepository;
import com.razvan.proiect.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Optional<CartItem> findByUserAndProductId(User user, Long productId) {
        CartItemKey id = new CartItemKey();
        id.setUserId(user.getId());
        id.setProductId(productId);
        return cartRepository.findById(id);
    }

    public void add(User user, Long productId) {
        Optional<CartItem> optionalCartItem = findByUserAndProductId(user, productId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartRepository.save(cartItem);
            return;
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        CartItem cartItem = new CartItem();
        CartItemKey id = new CartItemKey();
        id.setUserId(user.getId());
        id.setProductId(productId);
        cartItem.setId(id);
        cartItem.setUser(user);
        cartItem.setProduct(optionalProduct.get());
        cartItem.setQuantity(1);
        cartRepository.save(cartItem);
    }

    public void decrease(User user, Long productId) {
        Optional<CartItem> optionalCartItem = findByUserAndProductId(user, productId);
        if (optionalCartItem.isEmpty()) {
            return;
        }

        CartItem cartItem = optionalCartItem.get();
        if (cartItem.getQuantity() <= 1) {
            cartRepository.delete(cartItem);
            return;
        }

        cartItem.setQuantity(cartItem.getQuantity() - 1);
        cartRepository.save(cartItem);
    }

    public void remove(User user, Long productId) {
        CartItemKey id = new CartItemKey();
        id.setUserId(user.getId());
        id.setProductId(productId);
        cartRepository.deleteById(id);
    }
}
