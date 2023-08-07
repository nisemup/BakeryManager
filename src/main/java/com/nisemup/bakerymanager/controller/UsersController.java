package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.model.Users;
import com.nisemup.bakerymanager.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panel/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping()
    public String getUsers(Model model) {
        model.addAttribute("users", usersService.findAll());

        return "panel/users/users";
    }

    @GetMapping("/{users}")
    public String getUserProfile(Model model, @PathVariable Users users) {
        model.addAttribute("users", users);

        return "profile";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("users") Users users) {
        return "panel/users/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("users") Users users,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "panel/users/new";

        usersService.create(users);

        return "redirect:/panel/users";
    }

    @GetMapping("/{id}/edit")
    public String editUserProfile(Model model, @PathVariable("id") Long id) {
        if (usersService.findById(id).isPresent())
            // TODO: Make exception
            model.addAttribute("users", usersService.findById(id).get());

        return "panel/users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("users") Users users, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "panel/users/edit";

        usersService.update(id, users);
        return "redirect:/panel/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        usersService.deleteById(id);
        return "redirect:/panel/users";
    }
}
