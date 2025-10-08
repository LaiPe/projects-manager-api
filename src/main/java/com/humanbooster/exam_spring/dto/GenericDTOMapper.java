package com.humanbooster.exam_spring.dto;

public interface GenericDTOMapper<T, DTO> {
    DTO toDto(T entity);
    T toEntity(DTO dto);
}
