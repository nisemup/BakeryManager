package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.PaymentMethod;
import com.nisemup.bakerymanager.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panel/payments")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping()
    public String getPaymentMethods(Model model) {
        model.addAttribute("payments", paymentMethodService.findAll());

        return "panel/payments/payments";
    }

    @GetMapping("/{paymentMethod}")
    public String getPaymentMethodInfo(Model model, @PathVariable PaymentMethod paymentMethod) {
        model.addAttribute("payment", paymentMethod);

        return "panel/payments/info";
    }

    @GetMapping("/new")
    public String newPaymentMethod(@ModelAttribute("payment") PaymentMethod paymentMethod) {
        return "panel/payments/new";
    }

    @PostMapping()
    public String createPaymentMethod(@ModelAttribute("customer") PaymentMethod paymentMethod,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "panel/payments/new";

        paymentMethodService.create(paymentMethod);
        return "redirect:/panel/payments";
    }

    @GetMapping("/{id}/edit")
    public String editPaymentMethodInfo(Model model, @PathVariable("id") Long id) {
        model.addAttribute("payment", paymentMethodService.findById(id)
                .orElseThrow(() -> new NoEntityException("Payment method not found!")));

        return "panel/payments/edit";
    }

    @PatchMapping("/{id}")
    public String updatePaymentMethod(@ModelAttribute("payment") PaymentMethod paymentMethod,
                                      BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "panel/payments/edit";

        paymentMethodService.update(id, paymentMethod);
        return "redirect:/panel/payments";
    }

    @DeleteMapping("/{id}")
    public String deletePaymentMethod(@PathVariable("id") Long id) {
        paymentMethodService.deleteById(id);
        return "redirect:/panel/payments";
    }
}
