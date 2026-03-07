package com.btv.tag.mapper;

import com.btv.tag.dto.TagCreateRequest;
import com.btv.tag.dto.TagUpdateRequest;
import com.btv.tag.dto.TagResponse;
import com.btv.tag.entity.Tag;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TagMapper {

    // CREATE: DTO -> Entity
    Tag toEntity(TagCreateRequest request);

    // UPDATE: Partial update (ignore nulls)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(
            TagUpdateRequest request,
            @MappingTarget Tag tag
    );
  
    // RESPONSE: Entity -> DTO
    TagResponse toResponse(Tag tag);
}
