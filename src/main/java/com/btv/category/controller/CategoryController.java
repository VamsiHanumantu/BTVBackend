package com.btv.category.controller;

import com.btv.category.dto.CategoryCreateRequest;
import com.btv.category.dto.CategoryResponse;
import com.btv.category.dto.CategoryUpdateRequest;
import com.btv.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 1️⃣ Create Category
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryCreateRequest request
    ) {
        CategoryResponse response = categoryService.createCategory(request);
        return new ResponseEntity<CategoryResponse>(response,HttpStatus.CREATED);
    }

    // 2️⃣ Update Category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryUpdateRequest request
    ) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        return new ResponseEntity<CategoryResponse>(response,HttpStatus.OK);
    }

    // 3️⃣ Get Category by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @PathVariable UUID id
    ) {
        CategoryResponse response = categoryService.getCategoryById(id);
        return new ResponseEntity<CategoryResponse>(response,HttpStatus.OK);
    }

    // 4️⃣ Get All Active Categories
    @GetMapping("/active")
    public ResponseEntity<List<CategoryResponse>> getAllActiveCategories() {
        List<CategoryResponse> response = categoryService.getAllActiveCategories();
        return new ResponseEntity<List<CategoryResponse>>(response,HttpStatus.OK);
    }
}
