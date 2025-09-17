package com.humanbooster.exam_spring.dto;

import lombok.Data;

@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private Long creatorId;
}
