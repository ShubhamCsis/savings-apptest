package com.example.savingsapp.service;

import com.example.savingsapp.model.Customer;
import com.example.savingsapp.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCustomer_Success() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerNumber("123");

        when(customerRepository.findByCustomerNumber("123")).thenReturn(Optional.empty());
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer savedCustomer = customerService.addCustomer(customer);

        assertNotNull(savedCustomer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void addCustomer_Failure_DuplicateCustomerNumber() {
        Customer customer = new Customer();
        customer.setCustomerNumber("123");

        when(customerRepository.findByCustomerNumber("123")).thenReturn(Optional.of(customer));

        Exception exception = assertThrows(Exception.class, () -> customerService.addCustomer(customer));
        assertEquals("Customer number already exists", exception.getMessage());
    }

    @Test
    void calculateProjectedInvestment() {
        Customer customer = new Customer();
        customer.setInitialDeposit(1000);
        customer.setNumberOfYears(2);
        customer.setSavingsType("Savings De-luxe");

        double projectedInvestment = customerService.calculateProjectedInvestment(customer);

        assertEquals(1322.5, projectedInvestment, 0.01);
    }
}
