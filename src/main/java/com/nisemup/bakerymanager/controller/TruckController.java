package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.Truck;
import com.nisemup.bakerymanager.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panel/trucks")
public class TruckController {

    @Autowired
    private TruckService truckService;

    @GetMapping()
    public String getTrucks(Model model) {
        model.addAttribute("trucks", truckService.findAll());

        return "panel/trucks/trucks";
    }

    @GetMapping("/{truck}")
    public String getTruckInfo(Model model, @PathVariable Truck truck) {
        model.addAttribute("truck", truck);

        return "panel/trucks/info";
    }

    @GetMapping("/new")
    public String newTruck(@ModelAttribute("truck") Truck truck) {
        return "panel/trucks/new";
    }

    @PostMapping()
    public String createTruck(@ModelAttribute("truck") Truck truck,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "panel/trucks/new";

        truckService.create(truck);

        return "redirect:/panel/trucks";
    }

    @GetMapping("/{id}/edit")
    public String editTruckInfo(Model model, @PathVariable("id") Long id) {
        model.addAttribute("truck", truckService.findById(id)
                .orElseThrow(() -> new NoEntityException("Truck not found!")));

        return "/panel/trucks/edit";
    }

    @PatchMapping("/{id}")
    public String updateTruck(@ModelAttribute("truck") Truck truck, BindingResult bindingResult,
                                @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "panel/trucks/edit";

        truckService.update(id, truck);

        return "redirect:/panel/trucks";
    }

    @DeleteMapping("/{id}")
    public String deleteTruck(@PathVariable("id") Long id) {
        truckService.deleteById(id);

        return "redirect:/panel/trucks";
    }
}
