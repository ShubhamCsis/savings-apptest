package com.example.savingsapp.service;

import com.example.savingsapp.model.Customer;
import com.example.savingsapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer addCustomer(Customer customer) throws Exception {
        if (customerRepository.findByCustomerNumber(customer.getCustomerNumber()).isPresent()) {
            throw new Exception("Customer number already exists");
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        existingCustomer.setName(customer.getName());
        existingCustomer.setInitialDeposit(customer.getInitialDeposit());
        existingCustomer.setNumberOfYears(customer.getNumberOfYears());
        existingCustomer.setSavingsType(customer.getSavingsType());
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public double calculateProjectedInvestment(Customer customer) {
        double rate = customer.getSavingsType().equals("Savings De-luxe") ? 0.15 : 0.10;
        return customer.getInitialDeposit() * Math.pow(1 + rate, customer.getNumberOfYears());
    }
}
