package com.humanbooster.exam_spring.dto.task;

import com.humanbooster.exam_spring.dto.GenericDTOMapper;
import com.humanbooster.exam_spring.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateTaskMapper extends GenericDTOMapper<Task, CreateTaskDTO> {

    @Override
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "assignee.id", target = "assigneeId")
    CreateTaskDTO toDto(Task entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "projectId", target = "project.id")
    @Mapping(source = "assigneeId", target = "assignee.id")
    Task toEntity(CreateTaskDTO dto);
}
