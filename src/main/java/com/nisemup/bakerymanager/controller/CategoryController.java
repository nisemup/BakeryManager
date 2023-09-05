package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.Category;
import com.nisemup.bakerymanager.service.CategoryService;
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
@RequestMapping("/panel/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String getCategory(Model model, @Param("keyword") String keyword) {
        try {
            List<Category> categories = new ArrayList<>();

            if (keyword == null) {
                categories.addAll(categoryService.findAll());
            } else {
                categories.addAll(categoryService.findByNameContainingIgnoreCase(keyword));
                model.addAttribute("keyword", keyword);
            }

            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "panel/categories/categories";
    }

    @GetMapping("/new")
    public String newCategory(@ModelAttribute("category") Category category, Model model) {
        model.addAttribute("method", "POST");
        model.addAttribute("pageTitle", "Create new category");

        return "panel/categories/form";
    }

    @PostMapping()
    public String createCategory(@ModelAttribute("category") Category category,
                                 BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "panel/categories/form";

        categoryService.create(category);

        return "redirect:/panel/categories";
    }

    @GetMapping("/{id}")
    public String editCategoryInfo(Model model, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("category", categoryService.findById(id)
                    .orElseThrow(() -> new NoEntityException("Category with id " + id + " not found!")));
        } catch (NoEntityException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/panel/categories";
        }

        model.addAttribute("pageTitle", "Edit category (ID: " + id + ")");
        return "panel/categories/form";
    }

    @PatchMapping("/{id}")
    public String updateCategory(@ModelAttribute("category") Category category,
                                 @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.update(id, category);

            redirectAttributes.addFlashAttribute("message", "The category has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/panel/categories";
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateCategoryStatus(@PathVariable("id") Long id, @RequestParam ("active") boolean active) {
        try {
            categoryService.updateActiveStatus(id, active);

            String status = active ? "activated" : "disabled";
            String message = "The category with id " + id + " has been " + status;

            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        try {
            categoryService.deleteById(id);

            return ResponseEntity.ok("The category with id " + id + " has been deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
