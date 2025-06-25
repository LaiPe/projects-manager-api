package com.humanbooster.exam_spring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 70)
    @Size(min = 3, max = 70)
    private String username;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    @JsonManagedReference("creator-projects")
    private List<Project> projects;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    @JsonManagedReference("assignee-tasks")
    private List<Task> tasks;
}
