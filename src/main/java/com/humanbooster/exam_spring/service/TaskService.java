package com.humanbooster.exam_spring.service;

import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.model.Task;
import com.humanbooster.exam_spring.model.User;
import com.humanbooster.exam_spring.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public Task save(Task task, Long projectId, Long assigneeId) {
        Project project = new Project();
        project.setId(projectId);

        User assignee = new User();
        assignee.setId(assigneeId);

        task.setProject(project);
        task.setAssignee(assignee);
        return taskRepository.save(task);
    }

    public Task update(Task task, Long id) {
        task.setId(id);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Task> findTasksByProjectId(Long projectId) {
        Project project = new Project();
        project.setId(projectId);
        return taskRepository.findTaskByProject(project);
    }

    @Transactional(readOnly = true)
    public List<Task> findTasksByAssigneeId(Long assigneeId) {
        User assignee = new User();
        assignee.setId(assigneeId);
        return taskRepository.findTasksByAssignee(assignee);
    }

}
