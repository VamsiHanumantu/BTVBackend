package com.btv.tag.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagUpdateRequest {

    @Size(max = 50)
    private String name;

    @Size(max = 60)
    private String slug;

    @Size(max = 150)
    private String description;

    private Boolean isActive;
}
