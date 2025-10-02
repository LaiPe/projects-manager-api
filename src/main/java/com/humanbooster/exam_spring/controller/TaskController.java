package com.humanbooster.exam_spring.controller;

import com.humanbooster.exam_spring.dto.TaskDTO;
import com.humanbooster.exam_spring.dto.UpdateStatusTaskDTO;
import com.humanbooster.exam_spring.model.Task;
import com.humanbooster.exam_spring.service.TaskService;
import com.humanbooster.exam_spring.utils.ModelMapperUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        Optional<Task> fetchedTaskOpt = taskService.getById(updateStatusTaskDTO.getId());
        if (fetchedTaskOpt.isPresent()) {
            Task fetchedTask = fetchedTaskOpt.get();
            fetchedTask.setStatus(updateStatusTaskDTO.getStatus());
            taskService.update(fetchedTask, updateStatusTaskDTO.getId());
            return ResponseEntity.ok(modelMapperUtil.toTaskDTO(fetchedTask));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
