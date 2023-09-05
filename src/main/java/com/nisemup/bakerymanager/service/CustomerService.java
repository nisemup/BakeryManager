package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.Customer;
import com.nisemup.bakerymanager.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public void create(Customer customer) {
        if (customerRepository.findByAddress(customer.getAddress()).isPresent())
            return;

        customerRepository.save(customer);
    }

    public void update(Long id, Customer customer) {
        Customer updatableCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NoEntityException("Customer not found!"));

        updatableCustomer.setCompanyName(customer.getCompanyName());
        updatableCustomer.setAddress(customer.getAddress());
        updatableCustomer.setName(customer.getName());
        updatableCustomer.setPhoneNumber(customer.getPhoneNumber());

        customerRepository.save(updatableCustomer);
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> findByAddressContainingIgnoreCase(String keyword) {
        return customerRepository.findByAddressContainingIgnoreCase(keyword);
    }
}
