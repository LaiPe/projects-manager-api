package com.humanbooster.exam_spring.dto.task;

import com.humanbooster.exam_spring.dto.GenericDTOMapper;
import com.humanbooster.exam_spring.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdateStatusTaskMapper extends GenericDTOMapper<Task, UpdateStatusTaskDTO> {

    @Override
    UpdateStatusTaskDTO toDto(Task entity);

    @Override
    @Mapping(target = "title", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    Task toEntity(UpdateStatusTaskDTO dto);
}
