package com.humanbooster.exam_spring.utils;

import com.humanbooster.exam_spring.dto.ProjectDTO;
import com.humanbooster.exam_spring.dto.TaskDTO;
import com.humanbooster.exam_spring.dto.UserDTO;
import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.model.Task;
import com.humanbooster.exam_spring.model.TaskStatus;
import com.humanbooster.exam_spring.model.User;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModelMapperUtilTest {

    @Test
    void testUserToUserDTOAndBack() {
        User user = new User(1L, "alice", Collections.emptyList(), Collections.emptyList());
        UserDTO dto = ModelMapperUtil.toUserDTO(user);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getUsername(), dto.getUsername());

        User mapped = ModelMapperUtil.toUser(dto);
        assertEquals(user.getId(), mapped.getId());
        assertEquals(user.getUsername(), mapped.getUsername());
    }

    @Test
    void testProjectToProjectDTOAndBack() {
        User creator = new User(2L, "bob", null, null);
        Project project = new Project(10L, "Projet X", creator, Collections.emptyList());
        ProjectDTO dto = ModelMapperUtil.toProjectDTO(project);
        assertEquals(project.getId(), dto.getId());
        assertEquals(project.getName(), dto.getName());
        assertNotNull(dto.getCreator());

        Project mapped = ModelMapperUtil.toProject(dto);
        assertEquals(project.getId(), mapped.getId());
        assertEquals(project.getName(), mapped.getName());
        assertNotNull(mapped.getCreator());
    }

    @Test
    void testTaskToTaskDTOAndBack() {
        Project project = new Project(20L, "Projet Y", null, null);
        User assignee = new User(3L, "carol", null, null);
        Task task = new Task(100L, "Tâche 1", TaskStatus.TODO, project, assignee);
        TaskDTO dto = ModelMapperUtil.toTaskDTO(task);
        assertEquals(task.getId(), dto.getId());
        assertEquals(task.getTitle(), dto.getTitle());
        assertEquals(task.getStatus(), dto.getStatus());
        assertEquals(project.getId(), dto.getProjectId());
        assertEquals(assignee.getId(), dto.getAssigneeId());

        Task mapped = ModelMapperUtil.toTask(dto);
        assertEquals(task.getId(), mapped.getId());
        assertEquals(task.getTitle(), mapped.getTitle());
        assertEquals(task.getStatus(), mapped.getStatus());
        // Les champs project et assignee ne sont pas mappés dans toTask
        assertNull(mapped.getProject());
        assertNull(mapped.getAssignee());
    }

    @Test
    void testNullSafety() {
        assertNull(ModelMapperUtil.toUserDTO(null));
        assertNull(ModelMapperUtil.toUser(null));
        assertNull(ModelMapperUtil.toProjectDTO(null));
        assertNull(ModelMapperUtil.toProject(null));
        assertNull(ModelMapperUtil.toTaskDTO(null));
        assertNull(ModelMapperUtil.toTask(null));
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

        ProjectDTO projectDTO = ModelMapperUtil.toProjectDTO(project);
        assertEquals("Projet Z", projectDTO.getName());
        assertEquals("dave", projectDTO.getCreator().getUsername());
    }
} 