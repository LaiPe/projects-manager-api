package com.humanbooster.exam_spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 70)
    @Column(nullable = false, length = 70)
    private String title;

    @NotNull
    @Column(nullable = false)
    private TaskStatus status;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "project_id")
    private Project project;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "assignee_id")
    private User assignee;
}
