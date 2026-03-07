package com.btv.tag.controller;

import com.btv.tag.dto.TagCreateRequest;
import com.btv.tag.dto.TagResponse;
import com.btv.tag.dto.TagUpdateRequest;
import com.btv.tag.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // 1️⃣ Create Tag
    @PostMapping
    public ResponseEntity<TagResponse> createTag(
            @Valid @RequestBody TagCreateRequest request
    ) {
        TagResponse response = tagService.createTag(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2️⃣ Update Tag
    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateTag(
            @PathVariable UUID id,
            @Valid @RequestBody TagUpdateRequest request
    ) {
        TagResponse response = tagService.updateTag(id, request);
        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get Tag by ID
    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTagById(
            @PathVariable UUID id
    ) {
        TagResponse response = tagService.getTagById(id);
        return ResponseEntity.ok(response);
    }

    // 4️⃣ Get All Active Tags
    @GetMapping("/active")
    public ResponseEntity<List<TagResponse>> getAllActiveTags() {
        List<TagResponse> response = tagService.getAllActiveTags();
        return ResponseEntity.ok(response);
    }
}
