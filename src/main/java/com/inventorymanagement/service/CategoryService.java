package com.inventorymanagement.service;

import java.util.List;

import com.inventorymanagement.dto.CategoryDTO;

public interface CategoryService {

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Integer id);

    CategoryDTO updateCategory(Integer id, CategoryDTO categoryDTO);

    void deleteCategory(Integer id);

    void deleteAllCategories();
}