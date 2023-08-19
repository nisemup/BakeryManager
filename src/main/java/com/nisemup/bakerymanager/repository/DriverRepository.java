package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByLicense(String license);
}
