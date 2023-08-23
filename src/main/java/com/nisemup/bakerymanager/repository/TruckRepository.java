package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {
    Optional<Truck> findByNumberPlate(String plate);
}
