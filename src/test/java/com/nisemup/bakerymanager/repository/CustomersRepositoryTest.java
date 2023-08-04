package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.Customers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomersRepositoryTest {

    @Mock
    private CustomersRepository customersRepository;

    static final Long id = 1L;

    @Test
    void findByCustomerId_WhenIdExists_ReturnOptionalWithCustomer() {
        Customers customer = new Customers();
        customer.setCustomerId(id);
        when(customersRepository.findByCustomerId(id))
                .thenReturn(Optional.of(customer));

        Optional<Customers> result = customersRepository.findByCustomerId(id);

        assertTrue(result.isPresent());
        assertEquals(customer.getCustomerId(), result.get().getCustomerId());
        verify(customersRepository).findByCustomerId(id);
    }

    @Test
    void findByCustomerId_WhenIdDoesNotExists_ReturnEmptyOptional() {
        when(customersRepository.findByCustomerId(id))
                .thenReturn(Optional.empty());

        Optional<Customers> result = customersRepository.findByCustomerId(id);

        assertTrue(result.isEmpty());
        verify(customersRepository).findByCustomerId(id);
    }
}