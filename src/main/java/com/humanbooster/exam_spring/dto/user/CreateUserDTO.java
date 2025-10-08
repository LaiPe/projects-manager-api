package com.humanbooster.exam_spring.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {
    @NotBlank
    @Size(min = 3, max = 70)
    private String username;

    @NotBlank
    @Size(min = 3, max = 70)
    private String password;
}
