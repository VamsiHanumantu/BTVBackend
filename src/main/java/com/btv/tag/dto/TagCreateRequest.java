package com.btv.tag.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagCreateRequest {

    @NotBlank(message = "Tag name is required")
    @Size(max = 50)
    private String name;

    @NotBlank(message = "Slug is required")
    @Size(max = 60)
    private String slug;

    @Size(max = 150)
    private String description;
}
