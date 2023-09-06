package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.Driver;
import com.nisemup.bakerymanager.service.DriverService;
import com.nisemup.bakerymanager.service.TruckService;
import com.nisemup.bakerymanager.service.UserService;
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
@RequestMapping("/panel/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private UserService userService;

    @Autowired
    private TruckService truckService;

    @GetMapping()
    public String getDrivers(Model model, @Param("keyword") String keyword) {
        try {
            List<Driver> drivers = new ArrayList<>();

            if (keyword == null) {
                drivers.addAll(driverService.findAll());
            } else {
                drivers.addAll(driverService.findByLicenseContainingIgnoreCase(keyword));
                model.addAttribute("keyword", keyword);
            }

            model.addAttribute("drivers", drivers);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "panel/drivers/drivers";
    }

    @GetMapping("/new")
    public String newDriver(Model model, @ModelAttribute("driver") Driver driver) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("trucks", truckService.findAll());
        model.addAttribute("method", "POST");
        model.addAttribute("pageTitle", "Create new driver");

        return "panel/drivers/form";
    }

    @PostMapping()
    public String createDriver(@ModelAttribute("driver") Driver driver,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "panel/drivers/form";

        driverService.create(driver);

        return "redirect:/panel/drivers";
    }

    @GetMapping("/{id}")
    public String editDriverInfo(Model model, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("driver", driverService.findById(id)
                    .orElseThrow(() -> new NoEntityException("Driver not found!")));
        } catch (NoEntityException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        model.addAttribute("users", userService.findAll());
        model.addAttribute("trucks", truckService.findAll());
        model.addAttribute("method", "PATCH");
        model.addAttribute("pageTitle", "Edit driver (ID: " + id + ")");

        return "/panel/drivers/form";
    }

    @PatchMapping("/{id}")
    public String updateDriver(@ModelAttribute("driver") Driver driver, RedirectAttributes redirectAttributes,
                              @PathVariable("id") Long id) {

        try {
            driverService.update(id, driver);

            redirectAttributes.addFlashAttribute("message", "The driver has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/panel/drivers";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDriver(@PathVariable("id") Long id) {
        try {
            driverService.deleteById(id);

            return ResponseEntity.ok("The driver with id " + id + " has been deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}