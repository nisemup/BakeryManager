package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.model.Customers;
import com.nisemup.bakerymanager.repository.CustomersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomersServiceTest {

    @InjectMocks
    private CustomersService customersService;

    @Mock
    private CustomersRepository customersRepository;

    private static final Long id = 1L;

    @Test
    void findAll_WhenCustomersExists_ReturnsListOfCustomers() {
        List<Customers> expectedCustomers = new ArrayList<>();
        when(customersRepository.findAll())
                .thenReturn(expectedCustomers);

        List<Customers> actualCustomers = customersService.findAll();

        assertEquals(expectedCustomers, actualCustomers);
        verify(customersRepository).findAll();
    }

    @Test
    void findById_WhenIdExist_ReturnOptionalWithCustomer() {
        Customers expectedCustomer = new Customers();
        when(customersRepository.findByCustomerId(id))
                .thenReturn(Optional.of(expectedCustomer));

        Optional<Customers> actualCustomer = customersService.findById(id);

        // TODO: Make exception
        assertEquals(expectedCustomer, actualCustomer.get());
        verify(customersRepository).findByCustomerId(id);
    }

    @Test
    void create_WhenCustomerExist_MustCreateCustomer() {
        Customers existingCustomer = new Customers();
        existingCustomer.setCustomerId(id);
        when(customersRepository.findByCustomerId(existingCustomer.getCustomerId()))
                .thenReturn(Optional.of(existingCustomer));

        boolean result = customersService.create(existingCustomer);

        assertFalse(result);
        verify(customersRepository).findByCustomerId(existingCustomer.getCustomerId());
        verify(customersRepository, never()).save(existingCustomer);
    }

    @Test
    void create_WhenCustomerDoesNotExist_MustNotCreateCustomer() {
        Customers newCustomer = new Customers();
        newCustomer.setCustomerId(2L);

        when(customersRepository.findByCustomerId(newCustomer.getCustomerId()))
                .thenReturn(Optional.empty());

        boolean result = customersService.create(newCustomer);

        assertTrue(result);
        verify(customersRepository).findByCustomerId(newCustomer.getCustomerId());
        verify(customersRepository).save(newCustomer);
    }

    @Test
    void update_WhenCustomerExist_MustUpdateCustomer() {
        Customers existingCustomer = new Customers();
        Customers newCustomer = new Customers();
        newCustomer.setCompanyName("New Company");
        newCustomer.setAddress("New Address");
        newCustomer.setContactName("New Contact");
        newCustomer.setPhoneNumber("0954315678");

        when(customersRepository.findByCustomerId(id))
                .thenReturn(Optional.of(existingCustomer));
        when(customersRepository.save(any(Customers.class)))
                .thenReturn(newCustomer);

        customersService.update(id, newCustomer);

        verify(customersRepository, times(2)).findByCustomerId(id);
        verify(customersRepository).save(existingCustomer);
        assertEquals(newCustomer.getCompanyName(), existingCustomer.getCompanyName());
        assertEquals(newCustomer.getAddress(), existingCustomer.getAddress());
        assertEquals(newCustomer.getContactName(), existingCustomer.getContactName());
        assertEquals(newCustomer.getPhoneNumber(), existingCustomer.getPhoneNumber());
    }

    @Test
    void update_WhenCustomerDoesNotExist_MustNotUpdateCustomer() {
        Customers newCustomer = new Customers();

        when(customersRepository.findByCustomerId(id))
                .thenReturn(Optional.empty());

        when(customersRepository.findByCustomerId(id))
                .thenReturn(Optional.empty());

        customersService.update(id, newCustomer);

        verify(customersRepository).findByCustomerId(id);
        verify(customersRepository, never()).save(any(Customers.class));
    }

    @Test
    void deleteById_WhenCustomerExist_MustDeleteCustomer() {
        customersService.deleteById(id);

        verify(customersRepository).deleteById(id);
    }

}