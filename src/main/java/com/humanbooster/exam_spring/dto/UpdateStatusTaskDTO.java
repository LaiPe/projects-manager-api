package com.humanbooster.exam_spring.dto;

import com.humanbooster.exam_spring.model.TaskStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusTaskDTO {
    @NotNull
    @Min(1L)
    private Long id;

    @NotNull
    private TaskStatus status;
}
