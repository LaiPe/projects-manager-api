package com.humanbooster.exam_spring.dto;

import com.humanbooster.exam_spring.model.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskDTO {
    private Long id;

    @NotBlank
    @Size(min = 3, max = 70)
    private String title;

    @NotNull
    private TaskStatus status;

    @NotNull
    private Long projectId;

    @NotNull
    private Long assigneeId;
}
