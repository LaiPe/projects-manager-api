package com.humanbooster.exam_spring.dto.task;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTaskDTO {
    @NotBlank
    @Size(min = 3, max = 70)
    private String title;

    @Min(1)
    @NotNull
    private Long assigneeId;
}
