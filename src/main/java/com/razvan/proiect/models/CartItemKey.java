package com.razvan.proiect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class CartItemKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "product_id")
    private Long productId;

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }
}
