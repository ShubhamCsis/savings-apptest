package com.example.savingsapp.controller;

import com.example.savingsapp.model.Customer;
import com.example.savingsapp.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private Model model;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void index() {
        List<Customer> customers = new ArrayList<>();
        when(customerService.getAllCustomers()).thenReturn(customers);

        String viewName = customerController.index(model);

        assertEquals("index", viewName);
        verify(model, times(1)).addAttribute(eq("customers"), anyList());
    }

    @Test
    void addCustomer_Success() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerNumber("123");

        when(customerService.addCustomer(customer)).thenReturn(customer);

        String viewName = customerController.addCustomer(customer, model);

        assertEquals("redirect:/", viewName);
    }

    @Test
    void addCustomer_Failure() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerNumber("123");

        doThrow(new Exception("Customer number already exists")).when(customerService).addCustomer(customer);

        String viewName = customerController.addCustomer(customer, model);

        assertEquals("add", viewName);
        verify(model, times(1)).addAttribute(eq("error"), anyString());
    }
}
