package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.model.Customer;
import com.nisemup.bakerymanager.repository.CustomerRepository;
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
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    private static final Long id = 1L;

    @Test
    void findAll_WhenCustomersExists_ReturnsListOfCustomers() {
        List<Customer> expectedCustomers = new ArrayList<>();
        when(customerRepository.findAll())
                .thenReturn(expectedCustomers);

        List<Customer> actualCustomers = customerService.findAll();

        assertEquals(expectedCustomers, actualCustomers);
        verify(customerRepository).findAll();
    }

    @Test
    void findById_WhenIdExist_ReturnOptionalWithCustomer() {
        Customer expectedCustomer = new Customer();
        when(customerRepository.findById(id))
                .thenReturn(Optional.of(expectedCustomer));

        Optional<Customer> actualCustomer = customerService.findById(id);

        // TODO: Make exception
        assertEquals(expectedCustomer, actualCustomer.get());
        verify(customerRepository).findById(id);
    }

    @Test
    void create_WhenCustomerExist_MustCreateCustomer() {
        Customer existingCustomer = new Customer();
        existingCustomer.setId(id);

        when(customerRepository.findById(existingCustomer.getId()))
                .thenReturn(Optional.of(existingCustomer));

        customerService.create(existingCustomer);

        verify(customerRepository).findById(existingCustomer.getId());
        verify(customerRepository, never()).save(existingCustomer);
    }

    @Test
    void create_WhenCustomerDoesNotExist_MustNotCreateCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setId(2L);

        when(customerRepository.findById(newCustomer.getId()))
                .thenReturn(Optional.empty());

        customerService.create(newCustomer);

        verify(customerRepository).findById(newCustomer.getId());
        verify(customerRepository).save(newCustomer);
    }

    @Test
    void update_WhenCustomerExist_MustUpdateCustomer() {
        Customer existingCustomer = new Customer();
        Customer newCustomer = new Customer();
        newCustomer.setCompanyName("New Company");
        newCustomer.setAddress("New Address");
        newCustomer.setName("New Contact");
        newCustomer.setPhoneNumber("0954315678");

        when(customerRepository.findById(id))
                .thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class)))
                .thenReturn(newCustomer);

        customerService.update(id, newCustomer);

        verify(customerRepository, times(2)).findById(id);
        verify(customerRepository).save(existingCustomer);
        assertEquals(newCustomer.getCompanyName(), existingCustomer.getCompanyName());
        assertEquals(newCustomer.getAddress(), existingCustomer.getAddress());
        assertEquals(newCustomer.getName(), existingCustomer.getName());
        assertEquals(newCustomer.getPhoneNumber(), existingCustomer.getPhoneNumber());
    }

    @Test
    void update_WhenCustomerDoesNotExist_MustNotUpdateCustomer() {
        Customer newCustomer = new Customer();

        when(customerRepository.findById(id))
                .thenReturn(Optional.empty());

        when(customerRepository.findById(id))
                .thenReturn(Optional.empty());

        customerService.update(id, newCustomer);

        verify(customerRepository).findById(id);
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void deleteById_WhenCustomerExist_MustDeleteCustomer() {
        customerService.deleteById(id);

        verify(customerRepository).deleteById(id);
    }

}