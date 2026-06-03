package com.inventorymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventorymanagement.dto.CategoryDTO;
import com.inventorymanagement.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // SAVE Category
    @PostMapping("/save")
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.saveCategory(dto));
    }

    // GET ALL Categories
    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // GET BY ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    // UPDATE Category
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Integer id,
            @RequestBody CategoryDTO dto) {

        return ResponseEntity.ok(categoryService.updateCategory(id, dto));
    }

    // DELETE BY ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {

        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }

    // DELETE ALL
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllCategories() {

        categoryService.deleteAllCategories();
        return ResponseEntity.ok("All categories deleted successfully");
    }
}