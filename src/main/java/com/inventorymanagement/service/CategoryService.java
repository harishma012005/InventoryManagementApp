package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.entity.Category;

public interface CategoryService {

    Category saveCategory(
            Category category);

    List<Category> getAllCategories();

    Category getCategoryById(
            Integer id);

    Category updateCategory(
            Integer id,
            Category category);

    void deleteCategory(
            Integer id);

    void deleteAllCategories();
}
