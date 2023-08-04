package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.model.Customers;
import com.nisemup.bakerymanager.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersService {
    
    @Autowired
    private CustomersRepository customersRepository;

    public List<Customers> findAll() {
        return customersRepository.findAll();
    }

    public Optional<Customers> findById(Long id) {
        return customersRepository.findByCustomerId(id);
    }

    public boolean create(Customers customers) {
        Optional<Customers> customerAdd = customersRepository.findByCustomerId(customers.getCustomerId());

        if (customerAdd.isPresent()) {
            return false;
        }

        customersRepository.save(customers);

        return true;
    }

    public void update(Long id, Customers customers) {
        if (customersRepository.findByCustomerId(id).isPresent()) {
            Customers updatableCustomer = customersRepository.findByCustomerId(id).get();

            updatableCustomer.setCompanyName(customers.getCompanyName());
            updatableCustomer.setAddress(customers.getAddress());
            updatableCustomer.setContactName(customers.getContactName());
            updatableCustomer.setPhoneNumber(customers.getPhoneNumber());

            customersRepository.save(updatableCustomer);
        }
    }

    public void deleteById(Long id) {
        customersRepository.deleteById(id);
    }
}
