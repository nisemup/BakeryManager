package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.Customer;
import com.nisemup.bakerymanager.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/panel/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping()
    public String getCustomers(Model model, @Param("keyword") String keyword) {
        try {
            List<Customer> customers = new ArrayList<>();

            if (keyword == null) {
                customers.addAll(customerService.findAll());
            } else {
                customers.addAll(customerService.findByAddressContainingIgnoreCase(keyword));
                model.addAttribute("keyword", keyword);
            }

            model.addAttribute("customers", customers);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "panel/customers/customers";
    }

    @GetMapping("/new")
    public String newCustomer(@ModelAttribute("customer") Customer customer, Model model) {
        model.addAttribute("method", "POST");
        model.addAttribute("pageTitle", "Create new customer");

        return "panel/customers/form";
    }

    @PostMapping()
    public String createCustomer(@ModelAttribute("customer") Customer customer,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "panel/customers/form";

        customerService.create(customer);

        return "redirect:/panel/customers";
    }

    @GetMapping("/{id}")
    public String editCustomerInfo(Model model, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("customer", customerService.findById(id)
                    .orElseThrow(() -> new NoEntityException("Customer with id " + id + " not found!")));
        } catch (NoEntityException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/panel/customers";
        }

        model.addAttribute("method", "PATCH");
        model.addAttribute("pageTitle", "Edit customer (ID: " + id + ")");

        return "panel/customers/form";
    }

    @PatchMapping("/{id}")
    public String updateCustomer(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes,
                                 @PathVariable("id") Long id) {
        try {
            customerService.update(id, customer);

            redirectAttributes.addFlashAttribute("message", "The customer has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/panel/customers";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long id) {
        try {
            customerService.deleteById(id);

            return ResponseEntity.ok("The customer with id " + id + " has been deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
