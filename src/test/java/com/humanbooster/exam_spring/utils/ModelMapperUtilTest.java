package com.humanbooster.exam_spring.utils;

import com.humanbooster.exam_spring.dto.ProjectDTO;
import com.humanbooster.exam_spring.dto.TaskDTO;
import com.humanbooster.exam_spring.dto.UserDTO;
import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.model.Task;
import com.humanbooster.exam_spring.model.TaskStatus;
import com.humanbooster.exam_spring.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModelMapperUtilTest {

    private ModelMapperUtil modelMapperUtil;

    @BeforeEach
    public void setUp() {
        modelMapperUtil = Mappers.getMapper(ModelMapperUtil.class);
    }

    @Test
    void testUserToUserDTOAndBack() {
        User user = new User(1L, "alice", Collections.emptyList(), Collections.emptyList());
        UserDTO dto = modelMapperUtil.toUserDTO(user);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getUsername(), dto.getUsername());

        User mapped = modelMapperUtil.toUser(dto);
        assertEquals(user.getId(), mapped.getId());
        assertEquals(user.getUsername(), mapped.getUsername());
    }

    @Test
    void testProjectToProjectDTOAndBack() {
        User creator = new User(2L, "bob", null, null);
        Project project = new Project(10L, "Projet X", creator, Collections.emptyList());
        ProjectDTO dto = modelMapperUtil.toProjectDTO(project);
        assertEquals(project.getId(), dto.getId());
        assertEquals(project.getName(), dto.getName());
        assertNotNull(dto.getCreatorId());

        Project mapped = modelMapperUtil.toProject(dto);
        assertEquals(project.getId(), mapped.getId());
        assertEquals(project.getName(), mapped.getName());
        assertNotNull(mapped.getCreator());
    }

    @Test
    void testTaskToTaskDTOAndBack() {
        Project project = new Project(20L, "Projet Y", null, null);
        User assignee = new User(3L, "carol", null, null);
        Task task = new Task(100L, "Tâche 1", TaskStatus.TODO, project, assignee);
        task.setProject(project);
        task.setAssignee(assignee);
        
        TaskDTO dto = modelMapperUtil.toTaskDTO(task);
        assertEquals(task.getId(), dto.getId());
        assertEquals(task.getTitle(), dto.getTitle());
        assertEquals(task.getStatus(), dto.getStatus());
        assertEquals(project.getId(), dto.getProjectId());
        assertEquals(assignee.getId(), dto.getAssigneeId());

        Task mapped = modelMapperUtil.toTask(dto);
        assertEquals(task.getId(), mapped.getId());
        assertEquals(task.getTitle(), mapped.getTitle());
        assertEquals(task.getStatus(), mapped.getStatus());
        assertEquals(project.getId(), mapped.getProject().getId());
        assertEquals(assignee.getId(), mapped.getAssignee().getId());
    }

    @Test
    void testNullSafety() {
        assertNull(modelMapperUtil.toUserDTO(null));
        assertNull(modelMapperUtil.toUser(null));
        assertNull(modelMapperUtil.toProjectDTO(null));
        assertNull(modelMapperUtil.toProject(null));
        assertNull(modelMapperUtil.toTaskDTO(null));
        assertNull(modelMapperUtil.toTask(null));
    }

    @Test
    void testNestedMapping() {
        User creator = new User(4L, "dave", null, null);
        User assignee = new User(5L, "eve", null, null);
        Task task = new Task(200L, "Tâche 2", TaskStatus.DONE, null, assignee);
        Project project = new Project(30L, "Projet Z", creator, List.of(task));
        task.setProject(project);
        creator.setProjects(List.of(project));
        assignee.setTasks(List.of(task));

        ProjectDTO projectDTO = modelMapperUtil.toProjectDTO(project);
        assertEquals("Projet Z", projectDTO.getName());
        assertEquals(4L, projectDTO.getCreatorId());
    }
} 