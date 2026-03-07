package com.btv.tag.service;

import com.btv.tag.dto.TagCreateRequest;
import com.btv.tag.dto.TagResponse;
import com.btv.tag.dto.TagUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface TagService {

    TagResponse createTag(TagCreateRequest request);

    TagResponse updateTag(UUID tagId, TagUpdateRequest request);

    TagResponse getTagById(UUID tagId);

    List<TagResponse> getAllActiveTags();
}
