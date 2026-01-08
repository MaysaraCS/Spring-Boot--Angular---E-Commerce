package com.udemy.ecommerce.repository;

import com.udemy.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// entity type and primary key
public interface ProductRepository extends JpaRepository<Product,Long> {
}
