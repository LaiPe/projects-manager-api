package com.humanbooster.exam_spring.dto.user;

import com.humanbooster.exam_spring.dto.GenericDTOMapper;
import com.humanbooster.exam_spring.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GetUserMapper extends GenericDTOMapper<User, GetUserDTO> {
    @Override
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(GetUserDTO dto);
}
