package com.humanbooster.exam_spring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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

    @Column(nullable = false, length = 70)
    private String username;

    @Column(nullable = false, length = 70)
    private String password;

    @OneToMany(mappedBy = "creator")
    @JsonManagedReference("creator-projects")
    private List<Project> projects;

    @OneToMany(mappedBy = "assignee")
    @JsonManagedReference("assignee-tasks")
    private List<Task> tasks;
}
