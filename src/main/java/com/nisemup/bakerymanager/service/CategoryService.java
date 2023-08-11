package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.model.Category;
import com.nisemup.bakerymanager.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public void create(Category category) {
        // TODO: Make exception
        if (categoryRepository.findByName(category.getName()).isPresent())
            return;

        categoryRepository.save(category);
    }

    public void update(Long id, Category category) {
        // TODO: Make exception
        Category updatableCategory = categoryRepository.findById(id).orElseThrow();

        updatableCategory.setName(category.getName());
        updatableCategory.setDescription(category.getDescription());
        updatableCategory.setPicture(category.getPicture());
        updatableCategory.setActive(category.getActive());

        categoryRepository.save(updatableCategory);
    }
}
