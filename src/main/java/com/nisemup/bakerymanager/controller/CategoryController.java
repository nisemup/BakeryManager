package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.Category;
import com.nisemup.bakerymanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panel/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String getCategory(Model model) {
        model.addAttribute("categories", categoryService.findAll());

        return "panel/categories/categories";
    }

    @GetMapping("/{category}")
    public String getCategoryInfo(Model model, @PathVariable Category category) {
        model.addAttribute("category", category);

        return "panel/categories/info";
    }

    @GetMapping("/new")
    public String newCategory(@ModelAttribute("category") Category category) {
        return "panel/categories/new";
    }

    @PostMapping()
    public String createCategory(@ModelAttribute("category") Category category,
                                 BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "panel/categories/new";

        categoryService.create(category);

        return "redirect:/panel/categories";
    }

    @GetMapping("/{id}/edit")
    public String editCategoryInfo(Model model, @PathVariable("id") Long id) {
        model.addAttribute("category", categoryService.findById(id)
                .orElseThrow(() -> new NoEntityException("Category not found!")));

        return "/panel/categories/edit";
    }

    @PatchMapping("/{id}")
    public String updateCategory(@ModelAttribute("category") Category category, BindingResult bindingResult,
                                 @PathVariable("id") Long id) {

        if (bindingResult.hasErrors())
            return "panel/categories/edit";

        categoryService.update(id, category);
        return "redirect:/panel/categories";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteById(id);

        return "redirect:/panel/categories";
    }
}
