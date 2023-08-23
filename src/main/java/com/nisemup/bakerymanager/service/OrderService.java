package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.Order;
import com.nisemup.bakerymanager.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public void create(Order order) {
        if (orderRepository.findByUserId(order.getUserId()).isPresent())
            return;

        orderRepository.save(order);
    }

    public void update(Long id, Order order) {
        Order updatableOrder = orderRepository.findById(id)
                .orElseThrow(() -> new NoEntityException("Order not found!"));

        updatableOrder.setOrderDate(order.getOrderDate());
        updatableOrder.setDelivered(order.getDelivered());
        updatableOrder.setCustomerId(order.getCustomerId());
        updatableOrder.setDeliveredDate(order.getDeliveredDate());
        updatableOrder.setPaymentId(order.getPaymentId());
        updatableOrder.setUserId(order.getUserId());
        updatableOrder.setRequiredDate(order.getRequiredDate());
        updatableOrder.setTransactionStatus(order.getTransactionStatus());
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
