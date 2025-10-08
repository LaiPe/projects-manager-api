package com.humanbooster.exam_spring.controller;

import com.humanbooster.exam_spring.dto.project.ProjectDTO;
import com.humanbooster.exam_spring.dto.project.ProjectMapper;
import com.humanbooster.exam_spring.dto.task.GetTaskDTO;
import com.humanbooster.exam_spring.dto.task.GetTaskMapper;
import com.humanbooster.exam_spring.dto.user.CreateUserDTO;
import com.humanbooster.exam_spring.dto.user.CreateUserMapper;
import com.humanbooster.exam_spring.dto.user.GetUserDTO;
import com.humanbooster.exam_spring.dto.user.GetUserMapper;
import com.humanbooster.exam_spring.model.User;
import com.humanbooster.exam_spring.service.ProjectService;
import com.humanbooster.exam_spring.service.TaskService;
import com.humanbooster.exam_spring.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;

    private final CreateUserMapper createUserMapper;
    private final GetUserMapper getUserMapper;
    private final GetTaskMapper getTaskMapper;
    private final ProjectMapper projectMapper;


    @PostMapping
    public ResponseEntity<GetUserDTO> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        User savedUser = userService.create(createUserMapper.toEntity(createUserDTO));
        return ResponseEntity.ok(getUserMapper.toDto(savedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO> getUser(@PathVariable Long id) {
        return userService.getById(id)
                .map(user -> ResponseEntity.ok(getUserMapper.toDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/projects")
    public ResponseEntity<List<ProjectDTO>> getProjectsUser(@PathVariable Long id) {
        return ResponseEntity.ok(
                projectService.findProjectsByCreatorId(id)
                    .stream()
                    .map(projectMapper::toDto)
                    .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<GetTaskDTO>> getTasksUser(@PathVariable Long id) {
        return ResponseEntity.ok(
                taskService.getTasksByAssigneeId(id)
                    .stream()
                    .map(getTaskMapper::toDto)
                    .collect(Collectors.toList())
        );
    }

}
