package com.humanbooster.exam_spring.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank
    @Size(min = 3, max = 70)
    private String username;

    @NotBlank
    @Size(min = 3, max = 70)
    private String password;
}
