package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.Order;
import com.nisemup.bakerymanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByUserId(User user);
}
