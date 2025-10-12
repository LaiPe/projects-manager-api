package com.humanbooster.exam_spring.controller;

import com.humanbooster.exam_spring.dto.project.ProjectDTO;
import com.humanbooster.exam_spring.dto.project.ProjectMapper;
import com.humanbooster.exam_spring.dto.task.GetTaskDTO;
import com.humanbooster.exam_spring.dto.task.GetTaskMapper;
import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.service.ProjectService;
import com.humanbooster.exam_spring.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;

    private final ProjectMapper projectMapper;
    private final GetTaskMapper getTaskMapper;

    @PostMapping
    @PreAuthorize("@userService.getById(#projectDTO.creatorId).get().username == authentication.name")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody @Valid ProjectDTO projectDTO) {
        Project savedProject = projectService.create(projectMapper.toEntity(projectDTO));
        return ResponseEntity.ok(projectMapper.toDto(savedProject));

    }

    @GetMapping("/{id}")
    @PreAuthorize("@projectService.getById(#id).get().creator.username == authentication.name")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long id) {
        return projectService.getById(id)
                .map(project -> ResponseEntity.ok(projectMapper.toDto(project)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/tasks")
    @PreAuthorize("@projectService.getById(#id).get().creator.username == authentication.name")
    public ResponseEntity<List<GetTaskDTO>> getProjectTasks(@PathVariable Long id) {
        return ResponseEntity.ok(
                taskService.getTasksByProjectId(id)
                        .stream()
                        .map(getTaskMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

}
