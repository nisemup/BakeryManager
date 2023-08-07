package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
    Optional<Products> findByProductId(Long id);
}
