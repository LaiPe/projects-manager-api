package com.humanbooster.exam_spring.controller;

import com.humanbooster.exam_spring.dto.ProjectDTO;
import com.humanbooster.exam_spring.dto.TaskDTO;
import com.humanbooster.exam_spring.dto.UserDTO;
import com.humanbooster.exam_spring.model.User;
import com.humanbooster.exam_spring.service.ProjectService;
import com.humanbooster.exam_spring.service.TaskService;
import com.humanbooster.exam_spring.service.UserService;
import com.humanbooster.exam_spring.utils.ModelMapperUtil;
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

    private final ModelMapperUtil  modelMapperUtil;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User savedUser = userService.save(modelMapperUtil.toUser(userDTO));
        return ResponseEntity.ok(modelMapperUtil.toUserDTO(savedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(modelMapperUtil.toUserDTO(user)))
                .orElse(null);
    }

    @GetMapping("/{id}/projects")
    public ResponseEntity<List<ProjectDTO>> getProjectsUser(@PathVariable Long id) {
        return ResponseEntity.ok(
                projectService.findProjectsByCreatorId(id)
                    .stream()
                    .map(modelMapperUtil::toProjectDTO)
                    .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksUser(@PathVariable Long id) {
        return ResponseEntity.ok(
                taskService.findTasksByAssigneeId(id)
                    .stream()
                    .map(modelMapperUtil::toTaskDTO)
                    .collect(Collectors.toList())
        );
    }

}
