package com.btv.category.controller;

import com.btv.category.dto.CategoryCreateRequest;
import com.btv.category.dto.CategoryResponse;
import com.btv.category.dto.CategoryUpdateRequest;
import com.btv.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 1️⃣ Create Category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryCreateRequest request
    ) {
        CategoryResponse response = categoryService.createCategory(request);
        return new ResponseEntity<CategoryResponse>(response,HttpStatus.CREATED);
    }

    // 2️⃣ Update Category
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryUpdateRequest request
    ) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        return new ResponseEntity<CategoryResponse>(response,HttpStatus.OK);
    }

    // 3️⃣ Get Category by ID
    @PreAuthorize("hasAnyRole('USER','REPORTER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @PathVariable UUID id
    ) {
        CategoryResponse response = categoryService.getCategoryById(id);
        return new ResponseEntity<CategoryResponse>(response,HttpStatus.OK);
    }

    // 4️⃣ Get All Active Categories
    @PreAuthorize("hasAnyRole('USER','REPORTER','ADMIN')")
    @GetMapping("/active")
    public ResponseEntity<Page<CategoryResponse>> getAllActiveCategories(Pageable pageable) {
        Page<CategoryResponse> response = categoryService.getAllActiveCategories(pageable);
        return new ResponseEntity<Page<CategoryResponse>>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(Pageable pageable) {
        Page<CategoryResponse> response = categoryService.getAllCategories(pageable);
        return new ResponseEntity<Page<CategoryResponse>>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateCategory(@PathVariable UUID id) {
        categoryService.deactivateCategory(id);
        return ResponseEntity.noContent().build();
    }
}
