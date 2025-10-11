package com.humanbooster.exam_spring.dto.auth;

import com.humanbooster.exam_spring.dto.GenericDTOMapper;
import com.humanbooster.exam_spring.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoginMapper extends GenericDTOMapper<User, LoginDTO> {
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(LoginDTO dto);
}
