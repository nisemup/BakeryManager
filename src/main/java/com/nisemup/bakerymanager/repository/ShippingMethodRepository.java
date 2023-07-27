package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Long> {

}
