package com.razvan.proiect.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
