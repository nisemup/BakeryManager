package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.Driver;
import com.nisemup.bakerymanager.service.DriverService;
import com.nisemup.bakerymanager.service.TruckService;
import com.nisemup.bakerymanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String getDrivers(Model model) {
        model.addAttribute("drivers", driverService.findAll());

        return "panel/drivers/drivers";
    }

    @GetMapping("/{driver}")
    public String getDriverInfo(Model model, @PathVariable Driver driver) {
        model.addAttribute("driver", driver);

        return "panel/drivers/info";
    }

    @GetMapping("/new")
    public String newDriver(Model model, @ModelAttribute("driver") Driver driver) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("trucks", truckService.findAll());


        return "panel/drivers/new";
    }

    @PostMapping()
    public String createDriver(@ModelAttribute("driver") Driver driver,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "panel/drivers/new";

        driverService.create(driver);

        return "redirect:/panel/drivers";
    }

    @GetMapping("/{id}/edit")
    public String editDriverInfo(Model model, @PathVariable("id") Long id) {
        model.addAttribute("driver", driverService.findById(id)
                .orElseThrow(() -> new NoEntityException("Driver not found!")));

        model.addAttribute("users", userService.findAll());
        model.addAttribute("trucks", truckService.findAll());

        return "/panel/drivers/edit";
    }

    @PatchMapping("/{id}")
    public String updateDriver(@ModelAttribute("driver") Driver driver, BindingResult bindingResult,
                              @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "panel/drivers/edit";

        driverService.update(id, driver);

        return "redirect:/panel/drivers";
    }

    @DeleteMapping("/{id}")
    public String deleteDriver(@PathVariable("id") Long id) {
        driverService.deleteById(id);

        return "redirect:/panel/drivers";
    }
}