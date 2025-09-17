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
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @Size(min = 3, max = 100)
    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false, name = "creator_id")
    @JsonManagedReference("creator-projects")
    private User creator;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @JsonManagedReference("project-tasks")
    private List<Task> tasks;
}
