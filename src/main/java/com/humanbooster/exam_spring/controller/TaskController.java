package com.humanbooster.exam_spring.controller;

import com.humanbooster.exam_spring.dto.TaskDTO;
import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.model.Task;
import com.humanbooster.exam_spring.model.TaskStatus;
import com.humanbooster.exam_spring.model.User;
import com.humanbooster.exam_spring.repository.TaskRepository;
import com.humanbooster.exam_spring.service.TaskService;
import com.humanbooster.exam_spring.utils.ModelMapperUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final ModelMapperUtil modelMapperUtil;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody CreateTaskForm createTaskForm) {
        Task savedTask = taskService.save(
                modelMapperUtil.toTask(createTaskForm.getTaskDTO()),
                createTaskForm.getProjectId(),
                createTaskForm.getAssigneeId()
        );
        return ResponseEntity.ok(modelMapperUtil.toTaskDTO(savedTask));
    }

    @PatchMapping
    public ResponseEntity<TaskDTO> updateStatus(@RequestBody  TaskDTO taskDTO) {
        Optional<Task> fetchedTaskOpt = taskService.findById(taskDTO.getId());
        if (fetchedTaskOpt.isPresent()) {
            Task fetchedTask = fetchedTaskOpt.get();
            fetchedTask.setStatus(taskDTO.getStatus());
            taskService.update(fetchedTask, taskDTO.getId());
            return ResponseEntity.ok(modelMapperUtil.toTaskDTO(fetchedTask));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Data
    public static class CreateTaskForm {
        private TaskDTO taskDTO;
        private Long projectId;
        private Long assigneeId;
    }

}
