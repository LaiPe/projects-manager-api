package com.humanbooster.exam_spring.dto.project;

import com.humanbooster.exam_spring.dto.GenericDTOMapper;
import com.humanbooster.exam_spring.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper extends GenericDTOMapper<Project, ProjectDTO> {

    @Override
    @Mapping(source = "creator.id", target = "creatorId")
    ProjectDTO toDto(Project entity);

    @Override
    @Mapping(source = "creatorId", target = "creator.id")
    @Mapping(target = "tasks", ignore = true)
    Project toEntity(ProjectDTO dto);
}
