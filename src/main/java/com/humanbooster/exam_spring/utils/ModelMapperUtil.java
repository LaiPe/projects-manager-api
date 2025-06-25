package com.humanbooster.exam_spring.utils;

import com.humanbooster.exam_spring.dto.ProjectDTO;
import com.humanbooster.exam_spring.dto.TaskDTO;
import com.humanbooster.exam_spring.dto.UserDTO;
import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.model.Task;
import com.humanbooster.exam_spring.model.User;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperUtil {
    // Project <-> ProjectDTO
    public ProjectDTO toProjectDTO(Project project) {
        if (project == null) return null;
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setCreator(toUserDTO(project.getCreator()));
        return dto;
    }

    public Project toProject(ProjectDTO dto) {
        if (dto == null) return null;
        Project project = new Project();
        project.setId(dto.getId());
        project.setName(dto.getName());
        project.setCreator(toUser(dto.getCreator()));
        return project;
    }

    // Task <-> TaskDTO
    public TaskDTO toTaskDTO(Task task) {
        if (task == null) return null;
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setStatus(task.getStatus());
        dto.setProjectId(task.getProject() != null ? task.getProject().getId() : null);
        dto.setAssigneeId(task.getAssignee() != null ? task.getAssignee().getId() : null);
        return dto;
    }

    public Task toTask(TaskDTO dto) {
        if (dto == null) return null;
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setStatus(dto.getStatus());
        // Les champs project et assignee doivent être set à part si besoin (requête en base)
        return task;
    }

    // User <-> UserDTO
    public UserDTO toUserDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }

    public User toUser(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        return user;
    }
} 