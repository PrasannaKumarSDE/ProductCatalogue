package com.example.ProductCatalogue.Repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ProductCatalogue.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
