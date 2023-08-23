package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.Order;
import com.nisemup.bakerymanager.service.CustomerService;
import com.nisemup.bakerymanager.service.OrderService;
import com.nisemup.bakerymanager.service.PaymentMethodService;
import com.nisemup.bakerymanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panel/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping()
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());

        return "panel/orders/orders";
    }

    @GetMapping("/{order}")
    public String getOrderInfo(Model model, @PathVariable Order order) {
        model.addAttribute("order", order);

        return "panel/orders/info";
    }

    @GetMapping("/new")
    public String newOrder(Model model, @ModelAttribute("order") Order order) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("paymentMethods", paymentMethodService.findAll());

        return "panel/orders/new";
    }

    @PostMapping()
    public String createOrder(@ModelAttribute("order") Order order,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "panel/orders/new";

        orderService.create(order);

        return "redirect:/panel/orders";
    }

    @GetMapping("/{id}/edit")
    public String editOrderInfo(Model model, @PathVariable("id") Long id) {
        model.addAttribute("order", orderService.findById(id)
                .orElseThrow(() -> new NoEntityException("Order not found!")));

        model.addAttribute("users", userService.findAll());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("paymentMethods", paymentMethodService.findAll());

        return "/panel/orders/edit";
    }

    @PatchMapping("/{id}")
    public String updateOrder(@ModelAttribute("order") Order order, BindingResult bindingResult,
                               @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "panel/orders/edit";

        orderService.update(id, order);

        return "redirect:/panel/orders";
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteById(id);

        return "redirect:/panel/orders";
    }
}
