package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.model.Category;
import com.nisemup.bakerymanager.model.Products;
import com.nisemup.bakerymanager.service.CategoryService;
import com.nisemup.bakerymanager.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panel/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    private String getProducts(Model model) {
        model.addAttribute("products", productsService.findAll());

        return "panel/products/products";
    }

    @GetMapping("/{products}")
    public String getProductsInfo(Model model, @PathVariable Products products) {
        model.addAttribute("products", products);

        return "panel/products/info";
    }

    @GetMapping("/new")
    public String newProduct(Model model, @ModelAttribute("products") Products products) {
        model.addAttribute("category", categoryService.findAll());

        return "panel/products/new";
    }

    @PostMapping()
    public String createProduct(@ModelAttribute("products") Products products,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "panel/products/new";

        productsService.create(products);

        return "redirect:/panel/products";
    }

    @GetMapping("/{id}/edit")
    public String editProductInfo(Model model, @PathVariable("id") Long id) {
        if (productsService.findById(id).isPresent())
            model.addAttribute("products", productsService.findById(id).get());

        model.addAttribute("category", categoryService.findAll());

        return "/panel/products/edit";
    }

    @PatchMapping("/{id}")
    public String updateProduct(@ModelAttribute("products") Products products, BindingResult bindingResult,
                                @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "panel/products/edit";

        productsService.update(id, products);

        return "redirect:/panel/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productsService.deleteById(id);

        return"redirect:/panel/products";
    }

}
