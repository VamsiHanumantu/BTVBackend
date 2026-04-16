package com.btv.tag.controller;

import com.btv.tag.dto.TagCreateRequest;
import com.btv.tag.dto.TagResponse;
import com.btv.tag.dto.TagUpdateRequest;
import com.btv.tag.service.TagService;
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
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // 1️⃣ Create Tag
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TagResponse> createTag(
            @Valid @RequestBody TagCreateRequest request
    ) {
        TagResponse response = tagService.createTag(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2️⃣ Update Tag
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateTag(
            @PathVariable UUID id,
            @Valid @RequestBody TagUpdateRequest request
    ) {
        TagResponse response = tagService.updateTag(id, request);
        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get Tag by ID
    @PreAuthorize("hasAnyRole('USER','REPORTER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTagById(
            @PathVariable UUID id
    ) {
        TagResponse response = tagService.getTagById(id);
        return ResponseEntity.ok(response);
    }

    // 4️⃣ Get All Active Tags
    @PreAuthorize("hasAnyRole('USER','REPORTER','ADMIN')")
    @GetMapping("/active")
    public ResponseEntity<Page<TagResponse>> getAllActiveTags(Pageable pageable) {
        Page<TagResponse> response = tagService.getAllActiveTags(pageable);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<TagResponse>> getAllTags(Pageable pageable) {
        Page<TagResponse> response = tagService.getAllTags(pageable);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateTag(@PathVariable UUID id) {
        tagService.deactivateTag(id);
        return ResponseEntity.noContent().build();
    }
}
