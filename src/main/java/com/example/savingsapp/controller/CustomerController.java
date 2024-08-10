package com.example.savingsapp.controller;

import com.example.savingsapp.model.Customer;
import com.example.savingsapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String index(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "index";  // This should match the name of your template file (index.html)
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "add";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Customer customer, Model model) {
        try {
            customerService.addCustomer(customer);
            return "redirect:/";  // Redirects back to the home page after adding the customer
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "add";  // Return to the add page if there's an error
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Customer customer = customerService.getAllCustomers().stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("customer", customer);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editCustomer(@PathVariable("id") Long id, @ModelAttribute Customer customer) {
        customerService.updateCustomer(id, customer);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/";
    }

    @GetMapping("/projected/{id}")
    public String showProjectedInvestment(@PathVariable("id") Long id, Model model) {
        Customer customer = customerService.getAllCustomers().stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
        double projectedInvestment = customerService.calculateProjectedInvestment(customer);
        model.addAttribute("customer", customer);
        model.addAttribute("projectedInvestment", projectedInvestment);
        return "projected";
    }
}
