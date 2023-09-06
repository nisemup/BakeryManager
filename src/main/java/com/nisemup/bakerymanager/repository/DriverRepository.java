package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByLicense(String license);

    List<Driver> findByLicenseContainingIgnoreCase(String license);
}
