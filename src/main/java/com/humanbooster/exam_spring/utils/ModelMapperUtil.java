package com.humanbooster.exam_spring.utils;

import com.humanbooster.exam_spring.dto.ProjectDTO;
import com.humanbooster.exam_spring.dto.TaskDTO;
import com.humanbooster.exam_spring.dto.UserDTO;
import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.model.Task;
import com.humanbooster.exam_spring.model.User;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModelMapperUtil {
    
    @Mapping(source = "creator.id", target = "creatorId")
    public ProjectDTO toProjectDTO(Project project);

    @Mapping(source = "creatorId", target = "creator.id")
    @Mapping(target = "tasks", ignore = true)
    public Project toProject(ProjectDTO dto);


    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "assignee.id", target = "assigneeId")
    public TaskDTO toTaskDTO(Task task);

    @Mapping(source = "projectId", target = "project.id")
    @Mapping(source = "assigneeId", target = "assignee.id")
    public Task toTask(TaskDTO dto);


    public UserDTO toUserDTO(User user);

    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    public User toUser(UserDTO dto);
} 