package com.humanbooster.exam_spring.controller;

import com.humanbooster.exam_spring.dto.project.ProjectDTO;
import com.humanbooster.exam_spring.dto.project.ProjectMapper;
import com.humanbooster.exam_spring.dto.task.GetTaskDTO;
import com.humanbooster.exam_spring.dto.task.GetTaskMapper;
import com.humanbooster.exam_spring.dto.user.GetUserDTO;
import com.humanbooster.exam_spring.dto.user.GetUserMapper;
import com.humanbooster.exam_spring.service.ProjectService;
import com.humanbooster.exam_spring.service.TaskService;
import com.humanbooster.exam_spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;

    private final GetUserMapper getUserMapper;
    private final GetTaskMapper getTaskMapper;
    private final ProjectMapper projectMapper;


    @PostMapping
    public ResponseEntity<String> createUser() {
        return ResponseEntity.status(HttpStatus.GONE)
                .body("Vous ne pouvez plus créer un utilisateur via cette route, utilisez /api/auth/register");
    }

    @GetMapping("/{id}")
    @PreAuthorize("@userService.getById(#id).get().username == authentication.name")
    public ResponseEntity<GetUserDTO> getUser(@PathVariable Long id) {
        return userService.getById(id)
                .map(user -> ResponseEntity.ok(getUserMapper.toDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/projects")
    @PreAuthorize("@userService.getById(#id).get().username == authentication.name")
    public ResponseEntity<List<ProjectDTO>> getProjectsUser(@PathVariable Long id) {
        return ResponseEntity.ok(
                projectService.findProjectsByCreatorId(id)
                    .stream()
                    .map(projectMapper::toDto)
                    .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}/tasks")
    @PreAuthorize("@userService.getById(#id).get().username == authentication.name")
    public ResponseEntity<List<GetTaskDTO>> getTasksUser(@PathVariable Long id) {
        return ResponseEntity.ok(
                taskService.getTasksByAssigneeId(id)
                    .stream()
                    .map(getTaskMapper::toDto)
                    .collect(Collectors.toList())
        );
    }

}
