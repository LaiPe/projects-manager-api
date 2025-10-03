package com.humanbooster.exam_spring.dto.user;

import com.humanbooster.exam_spring.dto.GenericDTOMapper;
import com.humanbooster.exam_spring.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateUserMapper extends GenericDTOMapper<User, CreateUserDTO> {
    @Override
    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    User toEntity(CreateUserDTO dto);
}
