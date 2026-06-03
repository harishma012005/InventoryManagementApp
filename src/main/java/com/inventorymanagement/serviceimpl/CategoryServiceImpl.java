package com.inventorymanagement.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.dto.CategoryDTO;
import com.inventorymanagement.entity.Category;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.CategoryRepository;
import com.inventorymanagement.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Convert Entity → DTO
    private CategoryDTO convertToDTO(Category category) {

        CategoryDTO dto = new CategoryDTO();

        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());

        return dto;
    }

    // Convert DTO → Entity
    private Category convertToEntity(CategoryDTO dto) {

        Category category = new Category();

        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());

        return category;
    }

    // Save Category
    @Override
    public CategoryDTO saveCategory(CategoryDTO dto) {

        Category category = convertToEntity(dto);

        Category saved = categoryRepository.save(category);

        return convertToDTO(saved);
    }

    // Get All
    @Override
    public List<CategoryDTO> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get By ID
    @Override
    public CategoryDTO getCategoryById(Integer id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category Not Found With ID: " + id));

        return convertToDTO(category);
    }

    // Update
    @Override
    public CategoryDTO updateCategory(Integer id, CategoryDTO dto) {

        Category existing = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category Not Found With ID: " + id));

        existing.setCategoryName(dto.getCategoryName());
        existing.setDescription(dto.getDescription());

        Category updated = categoryRepository.save(existing);

        return convertToDTO(updated);
    }

    // Delete
    @Override
    public void deleteCategory(Integer id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category Not Found With ID: " + id));

        categoryRepository.delete(category);
    }

    // Delete All
    @Override
    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }
}