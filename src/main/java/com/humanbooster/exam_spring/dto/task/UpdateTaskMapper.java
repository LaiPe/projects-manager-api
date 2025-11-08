package com.humanbooster.exam_spring.dto.task;

import com.humanbooster.exam_spring.dto.GenericDTOMapper;
import com.humanbooster.exam_spring.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdateTaskMapper extends GenericDTOMapper<Task, UpdateTaskDTO> {
    @Override
    UpdateTaskDTO toDto(Task entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "assignee.id", source = "assigneeId")
    Task toEntity(UpdateTaskDTO dto);
}
