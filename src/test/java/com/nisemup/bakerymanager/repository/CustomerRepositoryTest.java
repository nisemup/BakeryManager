package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryTest {

    @Mock
    private CustomerRepository customerRepository;

    static final Long id = 1L;

    @Test
    void findById_WhenIdExists_ReturnOptionalWithCustomer() {
        Customer customer = new Customer();
        customer.setId(id);
        when(customerRepository.findById(id))
                .thenReturn(Optional.of(customer));

        Optional<Customer> result = customerRepository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(customer.getId(), result.get().getId());
        verify(customerRepository).findById(id);
    }

    @Test
    void findById_WhenIdDoesNotExists_ReturnEmptyOptional() {
        when(customerRepository.findById(id))
                .thenReturn(Optional.empty());

        Optional<Customer> result = customerRepository.findById(id);

        assertTrue(result.isEmpty());
        verify(customerRepository).findById(id);
    }
}