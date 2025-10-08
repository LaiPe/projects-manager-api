package com.humanbooster.exam_spring.dto.task;

import com.humanbooster.exam_spring.model.TaskStatus;

import lombok.Data;

@Data
public class GetTaskDTO {
    private Long id;
    private String title;
    private TaskStatus status;
    private Long projectId;
    private Long assigneeId;
}
