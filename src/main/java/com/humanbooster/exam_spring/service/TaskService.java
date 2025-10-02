package com.humanbooster.exam_spring.service;

import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.model.Task;
import com.humanbooster.exam_spring.model.User;
import com.humanbooster.exam_spring.repository.TaskRepository;
import com.humanbooster.exam_spring.service.generic.GenericJPAService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService extends GenericJPAService<Task, Long> {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        super(taskRepository);
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksByProjectId(Long projectId) {
        Project project = new Project();
        project.setId(projectId);
        return taskRepository.findTaskByProject(project);
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksByAssigneeId(Long assigneeId) {
        User assignee = new User();
        assignee.setId(assigneeId);
        return taskRepository.findTasksByAssignee(assignee);
    }

}
