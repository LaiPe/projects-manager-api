package com.humanbooster.exam_spring.controller;

import com.humanbooster.exam_spring.dto.task.*;
import com.humanbooster.exam_spring.model.Task;
import com.humanbooster.exam_spring.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final CreateTaskMapper createTaskMapper;
    private final UpdateTaskMapper updateTaskMapper;
    private final GetTaskMapper getTaskMapper;
    private final UpdateStatusTaskMapper updateStatusTaskMapper;

    @PostMapping
    @PreAuthorize("@projectService.getById(#dto.projectId).get().creator.username == authentication.name")
    public ResponseEntity<GetTaskDTO> createTask(@RequestBody @Valid CreateTaskDTO dto) {
        Task savedTask = taskService.create(createTaskMapper.toEntity(dto));
        return ResponseEntity.ok(getTaskMapper.toDto(savedTask));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize(
            "@taskService.getById(#id).get().project.creator.username == authentication.name " +
            "or @taskService.getById(#id).get().assignee.username == authentication.name"
    )
    public ResponseEntity<GetTaskDTO> updateStatus(@PathVariable Long id, @RequestBody @Valid UpdateStatusTaskDTO updateStatusTaskDTO) {
        return taskService.update(updateStatusTaskMapper.toEntity(updateStatusTaskDTO), id)
                .map(task -> ResponseEntity.ok(getTaskMapper.toDto(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@taskService.getById(#id).get().project.creator.username == authentication.name")
    public ResponseEntity<GetTaskDTO> updateTask(@PathVariable Long id, @RequestBody @Valid UpdateTaskDTO dto) {
        return taskService.update(updateTaskMapper.toEntity(dto), id)
                .map(task -> ResponseEntity.ok(getTaskMapper.toDto(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@taskService.getById(#id).get().project.creator.username == authentication.name")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        return taskService.deleteById(id)
                .map(task -> ResponseEntity.ok().build())
                .orElse(ResponseEntity.notFound().build());
    }
}
