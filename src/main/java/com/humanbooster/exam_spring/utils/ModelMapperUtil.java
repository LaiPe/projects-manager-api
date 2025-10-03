package com.humanbooster.exam_spring.utils;

import com.humanbooster.exam_spring.dto.project.ProjectDTO;
import com.humanbooster.exam_spring.dto.task.TaskDTO;
import com.humanbooster.exam_spring.dto.task.UpdateStatusTaskDTO;
import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.model.Task;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModelMapperUtil {
    
    @Mapping(source = "creator.id", target = "creatorId")
    ProjectDTO toProjectDTO(Project project);

    @Mapping(source = "creatorId", target = "creator.id")
    @Mapping(target = "tasks", ignore = true)
    Project toProject(ProjectDTO dto);


    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "assignee.id", target = "assigneeId")
    TaskDTO toTaskDTO(Task task);

    @Mapping(source = "projectId", target = "project.id")
    @Mapping(source = "assigneeId", target = "assignee.id")
    Task toTask(TaskDTO dto);

    UpdateStatusTaskDTO toUpdateStatusTaskDTO(Task task);
    Task toTask(UpdateStatusTaskDTO dto);
} 