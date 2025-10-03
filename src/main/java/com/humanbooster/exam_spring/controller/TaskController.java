package com.humanbooster.exam_spring.controller;

import com.humanbooster.exam_spring.dto.task.TaskDTO;
import com.humanbooster.exam_spring.dto.task.UpdateStatusTaskDTO;
import com.humanbooster.exam_spring.model.Task;
import com.humanbooster.exam_spring.service.TaskService;
import com.humanbooster.exam_spring.utils.ModelMapperUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final ModelMapperUtil modelMapperUtil;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskDTO dto) {
        Task savedTask = taskService.create(modelMapperUtil.toTask(dto));
        return ResponseEntity.ok(modelMapperUtil.toTaskDTO(savedTask));
    }

    @PatchMapping
    public ResponseEntity<TaskDTO> updateStatus(@RequestBody @Valid UpdateStatusTaskDTO updateStatusTaskDTO) {
        return taskService.update(modelMapperUtil.toTask(updateStatusTaskDTO), updateStatusTaskDTO.getId())
                .map(task -> ResponseEntity.ok(modelMapperUtil.toTaskDTO(task)))
                .orElse(ResponseEntity.notFound().build());
    }
}
