package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.Customer;
import com.nisemup.bakerymanager.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panel/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping()
    public String getCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());

        return "panel/customers/customers";
    }

    @GetMapping("/{customer}")
    public String getCustomerInfo(Model model, @PathVariable Customer customer) {
        model.addAttribute("customer", customer);

        return "panel/customers/info";
    }

    @GetMapping("/new")
    public String newCustomer(@ModelAttribute("customer") Customer customer) {
        return "panel/customers/new";
    }

    @PostMapping()
    public String createCustomer(@ModelAttribute("customer") Customer customer,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "panel/customers/new";

        customerService.create(customer);
        return "redirect:/panel/customers";
    }

    @GetMapping("/{id}/edit")
    public String editCustomerDetails(Model model, @PathVariable("id") Long id) {
        model.addAttribute("customer", customerService.findById(id)
                .orElseThrow(() -> new NoEntityException("Customer not found!")));

        return "panel/customers/edit";
    }

    @PatchMapping("/{id}")
    public String updateCustomer(@ModelAttribute("customer") Customer customer, BindingResult bindingResult,
                                 @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "panel/customers/edit";

        customerService.update(id, customer);
        return "redirect:/panel/customers";
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteById(id);
        return "redirect:/panel/customers";
    }
}
