package com.humanbooster.exam_spring.controller;

import com.humanbooster.exam_spring.dto.task.CreateTaskDTO;
import com.humanbooster.exam_spring.dto.task.CreateTaskMapper;
import com.humanbooster.exam_spring.dto.task.GetTaskDTO;
import com.humanbooster.exam_spring.dto.task.GetTaskMapper;
import com.humanbooster.exam_spring.dto.task.UpdateStatusTaskDTO;
import com.humanbooster.exam_spring.dto.task.UpdateStatusTaskMapper;
import com.humanbooster.exam_spring.model.Task;
import com.humanbooster.exam_spring.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final CreateTaskMapper createTaskMapper;
    private final GetTaskMapper getTaskMapper;
    private final UpdateStatusTaskMapper updateStatusTaskMapper;

    @PostMapping
    public ResponseEntity<GetTaskDTO> createTask(@RequestBody @Valid CreateTaskDTO dto) {
        Task savedTask = taskService.create(createTaskMapper.toEntity(dto));
        return ResponseEntity.ok(getTaskMapper.toDto(savedTask));
    }

    @PatchMapping
    public ResponseEntity<GetTaskDTO> updateStatus(@RequestBody @Valid UpdateStatusTaskDTO updateStatusTaskDTO) {
        return taskService.update(updateStatusTaskMapper.toEntity(updateStatusTaskDTO), updateStatusTaskDTO.getId())
                .map(task -> ResponseEntity.ok(getTaskMapper.toDto(task)))
                .orElse(ResponseEntity.notFound().build());
    }
}
