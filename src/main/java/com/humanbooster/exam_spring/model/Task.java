package com.humanbooster.exam_spring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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

    @Column(nullable = false, length = 70)
    private String title;

    @Column(nullable = false)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(nullable = false, name = "project_id")
    @JsonManagedReference("project-tasks")
    private Project project;

    @ManyToOne
    @JoinColumn(nullable = false, name = "assignee_id")
    @JsonManagedReference("assignee-tasks")
    private User assignee;
}
