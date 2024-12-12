package com.example.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.products.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
