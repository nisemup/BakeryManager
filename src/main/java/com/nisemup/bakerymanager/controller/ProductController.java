package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.Product;
import com.nisemup.bakerymanager.service.CategoryService;
import com.nisemup.bakerymanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/panel/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String getProducts(Model model) {
        model.addAttribute("products", productService.findAll());

        return "panel/products/products";
    }

    @GetMapping("/{product}")
    public String getProductInfo(Model model, @PathVariable Product product) {
        model.addAttribute("product", product);

        return "panel/products/info";
    }

    @GetMapping("/new")
    public String newProduct(Model model, @ModelAttribute("product") Product product) {
        model.addAttribute("category", categoryService.findAll());

        return "panel/products/new";
    }

    @PostMapping()
    public String createProduct(@ModelAttribute("product") Product product,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "panel/products/new";

        productService.create(product);

        return "redirect:/panel/products";
    }

    @GetMapping("/{id}/edit")
    public String editProductDetails(Model model, @PathVariable("id") Long id) {
        model.addAttribute("product", productService.findById(id)
                .orElseThrow(() -> new NoEntityException("Product not found!")));

        model.addAttribute("category", categoryService.findAll());

        return "/panel/products/edit";
    }

    @PatchMapping("/{id}")
    public String updateProduct(@ModelAttribute("product") Product product, BindingResult bindingResult,
                                @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "panel/products/edit";

        productService.update(id, product);

        return "redirect:/panel/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);

        return"redirect:/panel/products";
    }

}
