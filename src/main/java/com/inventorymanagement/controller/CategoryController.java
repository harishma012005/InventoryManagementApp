package com.inventorymanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.entity.Category;
import com.inventorymanagement.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Add Category
    @PostMapping
    public Map<String, Object> saveCategory(
    		@Valid
            @RequestBody Category category) {

        Category savedCategory =
                categoryService.saveCategory(
                category);

        Map<String, Object> response =
                new HashMap<>();

        response.put("status", 201);

        response.put(
                "message",
                "Category Saved Successfully");

        response.put(
                "data",
                savedCategory);

        return response;
    }

    // Get All Categories
    @GetMapping
    public List<Category> getAllCategories() {

        return categoryService
                .getAllCategories();
    }

    // Get Category By ID
    @GetMapping("/{id}")
    public Category getCategoryById(
            @PathVariable Integer id) {

        return categoryService
                .getCategoryById(id);
    }

    // Update Category
    @PutMapping("/{id}")
    public Map<String, Object> updateCategory(
            @PathVariable Integer id,
            @Valid
            @RequestBody Category category) {

        Category updatedCategory =
                categoryService.updateCategory(
                id, category);

        Map<String, Object> response =
                new HashMap<>();

        response.put("status", 200);

        response.put(
                "message",
                "Category Updated Successfully");

        response.put(
                "data",
                updatedCategory);

        return response;
    }

    // Delete Category
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteCategory(
            @PathVariable Integer id) {

        categoryService.deleteCategory(id);

        Map<String, Object> response =
                new HashMap<>();

        response.put("status", 200);

        response.put(
                "message",
                "Category Deleted Successfully");

        return response;
    }

    // Delete All Categories
    @DeleteMapping
    public Map<String, Object>
    deleteAllCategories() {

        categoryService.deleteAllCategories();

        Map<String, Object> response =
                new HashMap<>();

        response.put("status", 200);

        response.put(
                "message",
                "All Categories Deleted Successfully");

        return response;
    }
}