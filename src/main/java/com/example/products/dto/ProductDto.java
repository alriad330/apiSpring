package com.example.products.dto;

import com.example.products.utils.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class ProductDto {

    private Long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private Double price;
    private Integer quantity;
    private String internalReference;
    private Long shellId;
    private InventoryStatus inventoryStatus;
    private Float rating;
    private Integer createdAt;
    private Integer updatedAt;
}
