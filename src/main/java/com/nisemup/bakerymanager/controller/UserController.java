package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.model.User;
import com.nisemup.bakerymanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panel/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findAll());

        return "panel/users/users";
    }

    @GetMapping("/{user}")
    public String getProfile(Model model, @PathVariable User user) {
        model.addAttribute("users", user);

        return "profile";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "panel/users/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "panel/users/new";

        userService.create(user);

        return "redirect:/panel/users";
    }

    @GetMapping("/{id}/edit")
    public String editUserProfile(Model model, @PathVariable("id") Long id) {
        if (userService.findById(id).isPresent())
            // TODO: Make exception
            model.addAttribute("user", userService.findById(id).get());

        return "panel/users/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("users") User user, BindingResult bindingResult,
                             @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "panel/users/edit";

        userService.update(id, user);
        return "redirect:/panel/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/panel/users";
    }
}
