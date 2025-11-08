package com.humanbooster.exam_spring.dto.task;

import com.humanbooster.exam_spring.model.TaskStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusTaskDTO {
    @NotNull
    private TaskStatus status;
}
