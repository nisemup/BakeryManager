package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.model.Products;
import com.nisemup.bakerymanager.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    public List<Products> findAll() {
        return productsRepository.findAll();
    }

    public Optional<Products> findById(Long id) {
        return productsRepository.findByProductId(id);
    }

    public boolean create(Products products) {
        Optional<Products> productsAdd = productsRepository.findByProductId(products.getProductId());

        if (productsAdd.isPresent())
            return false;

        productsRepository.save(products);

        return true;
    }

    public void update(Long id, Products products) {
        if (productsRepository.findByProductId(id).isPresent()) {
            Products updatableProducts = productsRepository.findByProductId(id).get();

            updatableProducts.setProductName(products.getProductName());
            updatableProducts.setProductDescription(products.getProductDescription());
            updatableProducts.setDiscount(products.getDiscount());
            updatableProducts.setPrice(products.getPrice());
            updatableProducts.setCategoryId(products.getCategoryId());
            updatableProducts.setDiscountAvailable(products.getDiscountAvailable());

            productsRepository.save(updatableProducts);
        }
    }

    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }
}

