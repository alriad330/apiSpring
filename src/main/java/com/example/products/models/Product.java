package com.example.products.models;

import com.example.products.utils.InventoryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    private String description;
    private String image;
    private String category;
    @Column(nullable = false)
    private Float price;
    private Integer quantity;
    @Column(name = "internal_reference")
    private String internalReference;
    @Column(name = "shell_id", unique = true)
    private Long shellId;
    @Enumerated(EnumType.STRING)
    @Column(name = "inventory_status")
    private InventoryStatus inventoryStatus;
    private Float rating;
    @Column(name = "created_at")
    private Integer createdAt;
    @Column(name = "updated_at")
    private Integer updatedAt;

}
/*
public record Product (@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
                       @Column(name = "code") String code, @Column(name="name") String name,
                       String description, String image, String category, Double price,
                       Integer quantity, String internalReference, Long shellId,
                       @Enumerated(EnumType.STRING) InventoryStatus inventoryStatus, Float rating,
                       Timestamp createdAt, Timestamp updatedAt) {}
 */