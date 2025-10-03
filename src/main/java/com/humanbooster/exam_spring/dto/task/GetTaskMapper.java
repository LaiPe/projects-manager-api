package com.humanbooster.exam_spring.dto.task;

import com.humanbooster.exam_spring.dto.GenericDTOMapper;
import com.humanbooster.exam_spring.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GetTaskMapper extends GenericDTOMapper<Task, GetTaskDTO> {

    @Override
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "assignee.id", target = "assigneeId")
    GetTaskDTO toDto(Task entity);

    @Override
    @Mapping(source = "projectId", target = "project.id")
    @Mapping(source = "assigneeId", target = "assignee.id")
    Task toEntity(GetTaskDTO dto);
}
