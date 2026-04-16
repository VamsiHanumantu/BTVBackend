package com.btv.tag.service;

import com.btv.tag.dto.TagCreateRequest;
import com.btv.tag.dto.TagResponse;
import com.btv.tag.dto.TagUpdateRequest;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    TagResponse createTag(TagCreateRequest request);

    TagResponse updateTag(UUID tagId, TagUpdateRequest request);

    TagResponse getTagById(UUID tagId);

    Page<TagResponse> getAllTags(Pageable pageable);

    Page<TagResponse> getAllActiveTags(Pageable pageable);

    void deactivateTag(UUID tagId);
}
