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
        return categoryRepository.findByCategoryId(id);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public boolean create(Category category) {
        Optional<Category> categoryAdd = categoryRepository.findByCategoryId(category.getCategoryId());

        if (categoryAdd.isPresent())
            return false;

        categoryRepository.save(category);

        return true;
    }

    public void update(Long id, Category category) {
        if (categoryRepository.findByCategoryId(id).isPresent()) {
            // TODO: Make exception
            Category updatableCategory = categoryRepository.findByCategoryId(id).get();

            updatableCategory.setCategoryName(category.getCategoryName());
            updatableCategory.setCategoryDescription(category.getCategoryDescription());
            updatableCategory.setPicture(category.getPicture());
            updatableCategory.setActive(category.getActive());

            categoryRepository.save(updatableCategory);
        }
    }
}
