package com.matheuscordeiro.salesapi.domain.repository;

import com.matheuscordeiro.salesapi.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}

