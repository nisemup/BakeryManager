package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.model.Customers;
import com.nisemup.bakerymanager.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panel/customers")
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    @GetMapping()
    public String getCustomers(Model model) {
        model.addAttribute("customers", customersService.findAll());

        return "panel/customers/customers";
    }

    @GetMapping("/{customers}")
    public String getCustomerDetails(Model model, @PathVariable Customers customers) {
        model.addAttribute("customers", customers);

        return "panel/customers/customerDetails";
    }

    @GetMapping("/new")
    public String newCustomers(@ModelAttribute("customers") Customers customers) {
        return "panel/customers/new";
    }

    @PostMapping()
    public String createCustomers(@ModelAttribute("customers") Customers customers,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "panel/customers/new";

        customersService.create(customers);
        return "redirect:/panel/customers";
    }

    @GetMapping("/{id}/edit")
    public String editUserProfile(Model model, @PathVariable("id") Long id) {
        // TODO: Make exception
        if (customersService.findById(id).isPresent()) {
            model.addAttribute("customers", customersService.findById(id).get());
        }

        return "panel/customers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("users") Customers customers, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "panel/customers/edit";

        customersService.update(id, customers);
        return "redirect:/panel/customers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        customersService.deleteById(id);
        return "redirect:/panel/customers";
    }
}
