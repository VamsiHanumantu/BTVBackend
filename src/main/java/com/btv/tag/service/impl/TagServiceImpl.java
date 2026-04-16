package com.btv.tag.service.impl;

import com.btv.common.exception.ResourceNotFoundException;
import com.btv.tag.dto.TagCreateRequest;
import com.btv.tag.dto.TagResponse;
import com.btv.tag.dto.TagUpdateRequest;
import com.btv.tag.entity.Tag;
import com.btv.tag.mapper.TagMapper;
import com.btv.tag.repository.TagRepository;
import com.btv.tag.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagResponse createTag(TagCreateRequest request) {

        Tag tag = tagMapper.toEntity(request);
        Tag savedTag = tagRepository.save(tag);

        return tagMapper.toResponse(savedTag);
    }

    @Override
    public TagResponse updateTag(UUID tagId, TagUpdateRequest request) {

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));

        tagMapper.updateEntity(request, tag);
        // dirty checking will update DB

        return tagMapper.toResponse(tag);
    }

    @Override
    public TagResponse getTagById(UUID tagId) {

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));

        return tagMapper.toResponse(tag);
    }

    @Override
    public Page<TagResponse> getAllActiveTags(Pageable pageable) {

        return tagRepository.findByIsActiveTrue(pageable)
                .map(tagMapper::toResponse);
    }

    @Override
    public Page<TagResponse> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable)
                .map(tagMapper::toResponse);
    }

    @Override
    public void deactivateTag(UUID tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
        tag.setIsActive(false);
    }
}
