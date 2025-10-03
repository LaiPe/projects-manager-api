package com.humanbooster.exam_spring.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectDTO {
    private Long id;

    @Size(min = 3, max = 100)
    @NotBlank
    private String name;

    @NotNull
    private Long creatorId;
}
