package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.model.Category;
import com.nisemup.bakerymanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panel/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String getCategory(Model model) {
        model.addAttribute("category", categoryService.findAll());

        return "panel/category/category";
    }

    @GetMapping("/{category}")
    public String getCategoryInfo(Model model, @PathVariable Category category) {
        model.addAttribute("category", category);

        return "panel/category/info";
    }

    @GetMapping("/new")
    public String newCategory(@ModelAttribute("category") Category category) {
        return "panel/category/new";
    }

    @PostMapping()
    public String createCategory(@ModelAttribute("category") Category category,
                                 BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "panel/category/new";

        categoryService.create(category);

        return "redirect:/panel/category";
    }

    @GetMapping("/{id}/edit")
    public String editCategoryInfo(Model model, @PathVariable("id") Long id) {
        if (categoryService.findById(id).isPresent())
            // TODO: Make exception
            model.addAttribute("category", categoryService.findById(id).get());

        return "/panel/category/edit";
    }

    @PatchMapping("/{id}")
    public String updateCategory(@ModelAttribute("category") Category category, BindingResult bindingResult,
                                 @PathVariable("id") Long id) {

        if (bindingResult.hasErrors())
            return "panel/category/edit";

        categoryService.update(id, category);
        return "redirect:/panel/category";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteById(id);

        return "redirect:/panel/category";
    }
}
