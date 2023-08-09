package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.model.Product;
import com.nisemup.bakerymanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void create(Product product) {
        // TODO: Make exception
        if (productRepository.findByName(product.getName()).isPresent())
            return;

        productRepository.save(product);
    }

    public void update(Long id, Product product) {
        // TODO: Make exception
        Product updatableProduct = productRepository.findById(id).orElseThrow();

        updatableProduct.setName(product.getName());
        updatableProduct.setDescription(product.getDescription());
        updatableProduct.setDiscount(product.getDiscount());
        updatableProduct.setPrice(product.getPrice());
        updatableProduct.setCategoryId(product.getCategoryId());
        updatableProduct.setDiscountAvailable(product.getDiscountAvailable());

        productRepository.save(updatableProduct);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}

