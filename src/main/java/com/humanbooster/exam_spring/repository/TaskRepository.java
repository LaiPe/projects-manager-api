package com.humanbooster.exam_spring.repository;

import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.model.Task;
import com.humanbooster.exam_spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTasksByAssignee(User assignee);
    List<Task> findTaskByProject(Project project);
}
