package com.humanbooster.exam_spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;

    @NotBlank
    @Size(min = 3, max = 70)
    private String username;
}
