package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long> {
    Optional<Customers> findByCustomerId(Long id);
}
