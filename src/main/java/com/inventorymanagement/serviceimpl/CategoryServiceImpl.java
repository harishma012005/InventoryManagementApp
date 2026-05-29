package com.inventorymanagement.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.entity.Category;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.CategoryRepository;
import com.inventorymanagement.service.CategoryService;

@Service
public class CategoryServiceImpl
        implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Save Category
    @Override
    public Category saveCategory(
            Category category) {

        return categoryRepository.save(
                category);
    }

    // Get All Categories
    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    // Get Category By ID
    @Override
    public Category getCategoryById(
            Integer id) {

        return categoryRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Category Not Found With ID : "
                + id));
    }

    // Update Category
    @Override
    public Category updateCategory(
            Integer id,
            Category category) {

        Category existingCategory =
                categoryRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Category Not Found With ID : "
                + id));

        existingCategory.setCategoryName(
                category.getCategoryName());

        existingCategory.setDescription(
                category.getDescription());

        return categoryRepository.save(
                existingCategory);
    }

    // Delete Category
    @Override
    public void deleteCategory(
            Integer id) {

        Category category =
                categoryRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                "Category Not Found With ID : "
                + id));

        categoryRepository.delete(category);
    }

    // Delete All Categories
    @Override
    public void deleteAllCategories() {

        categoryRepository.deleteAll();
    }
}