package com.humanbooster.exam_spring;

import com.humanbooster.exam_spring.dto.project.ProjectDTO;
import com.humanbooster.exam_spring.dto.project.ProjectMapper;
import com.humanbooster.exam_spring.dto.task.CreateTaskDTO;
import com.humanbooster.exam_spring.dto.task.CreateTaskMapper;
import com.humanbooster.exam_spring.dto.task.GetTaskDTO;
import com.humanbooster.exam_spring.dto.task.GetTaskMapper;
import com.humanbooster.exam_spring.dto.task.UpdateStatusTaskDTO;
import com.humanbooster.exam_spring.dto.task.UpdateStatusTaskMapper;
import com.humanbooster.exam_spring.dto.user.CreateUserDTO;
import com.humanbooster.exam_spring.dto.user.CreateUserMapper;
import com.humanbooster.exam_spring.dto.user.GetUserDTO;
import com.humanbooster.exam_spring.dto.user.GetUserMapper;
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

public class DtoMappersTest {

    private ProjectMapper projectMapper;

    private CreateUserMapper createUserMapper;
    private GetUserMapper getUserMapper;

    private CreateTaskMapper createTaskMapper;
    private GetTaskMapper getTaskMapper;
    private UpdateStatusTaskMapper updateStatusTaskMapper;

    @BeforeEach
    public void setUp() {
        projectMapper = Mappers.getMapper(ProjectMapper.class);

        createUserMapper = Mappers.getMapper(CreateUserMapper.class);
        getUserMapper = Mappers.getMapper(GetUserMapper.class);

        createTaskMapper = Mappers.getMapper(CreateTaskMapper.class);
        getTaskMapper = Mappers.getMapper(GetTaskMapper.class);
        updateStatusTaskMapper = Mappers.getMapper(UpdateStatusTaskMapper.class);
    }

    @Test
    void testUserToUserDTOAndBack() {
        User user = new User(1L, "alice", "password", Collections.emptyList(), Collections.emptyList());
        GetUserDTO getUserDTO = getUserMapper.toDto(user);
        assertEquals(user.getId(), getUserDTO.getId());
        assertEquals(user.getUsername(), getUserDTO.getUsername());

        User getUserMapperEntity = getUserMapper.toEntity(getUserDTO);
        assertEquals(user.getId(), getUserMapperEntity.getId());
        assertEquals(user.getUsername(), getUserMapperEntity.getUsername());


        CreateUserDTO createUserDTO = createUserMapper.toDto(user);
        assertEquals(user.getUsername(), createUserDTO.getUsername());
        assertEquals(user.getPassword(), createUserDTO.getPassword());

        User createUserMapperEntity = createUserMapper.toEntity(createUserDTO);
        assertEquals(user.getUsername(), createUserMapperEntity.getUsername());
        assertEquals(user.getPassword(), createUserMapperEntity.getPassword());
    }

    @Test
    void testProjectToProjectDTOAndBack() {
        User creator = new User(2L, "bob", "password", Collections.emptyList(), Collections.emptyList());
        Project project = new Project(10L, "Projet X", creator, Collections.emptyList());
        ProjectDTO dto = projectMapper.toDto(project);
        assertEquals(project.getId(), dto.getId());
        assertEquals(project.getName(), dto.getName());
        assertNotNull(dto.getCreatorId());

        Project mapped = projectMapper.toEntity(dto);
        assertEquals(project.getId(), mapped.getId());
        assertEquals(project.getName(), mapped.getName());
        assertNotNull(mapped.getCreator());
    }

    @Test
    void testTaskToTaskDTOAndBack() {
        Project project = new Project(20L, "Projet Y", null, null);
        User assignee = new User(3L, "carol", "password", Collections.emptyList(), Collections.emptyList());
        Task task = new Task(100L, "Tâche 1", TaskStatus.TODO, project, assignee);
        task.setProject(project);
        task.setAssignee(assignee);
        
        // Test GetTaskMapper
        GetTaskDTO dto = getTaskMapper.toDto(task);
        assertEquals(task.getId(), dto.getId());
        assertEquals(task.getTitle(), dto.getTitle());
        assertEquals(task.getStatus(), dto.getStatus());
        assertEquals(project.getId(), dto.getProjectId());
        assertEquals(assignee.getId(), dto.getAssigneeId());

        Task mapped = getTaskMapper.toEntity(dto);
        assertEquals(task.getId(), mapped.getId());
        assertEquals(task.getTitle(), mapped.getTitle());
        assertEquals(task.getStatus(), mapped.getStatus());
        assertEquals(project.getId(), mapped.getProject().getId());
        assertEquals(assignee.getId(), mapped.getAssignee().getId());

        // Test CreateTaskMapper
        CreateTaskDTO createDto = createTaskMapper.toDto(task);
        assertEquals(task.getTitle(), createDto.getTitle());
        assertEquals(task.getStatus(), createDto.getStatus());
        assertEquals(project.getId(), createDto.getProjectId());
        assertEquals(assignee.getId(), createDto.getAssigneeId());

        Task createMapped = createTaskMapper.toEntity(createDto);
        assertEquals(task.getTitle(), createMapped.getTitle());
        assertEquals(task.getStatus(), createMapped.getStatus());
        assertEquals(project.getId(), createMapped.getProject().getId());
        assertEquals(assignee.getId(), createMapped.getAssignee().getId());

        // Test UpdateStatusTaskMapper
        UpdateStatusTaskDTO updateDto = updateStatusTaskMapper.toDto(task);
        assertEquals(task.getStatus(), updateDto.getStatus());

        Task updateMapped = updateStatusTaskMapper.toEntity(updateDto);
        assertEquals(task.getId(), updateMapped.getId());
        assertEquals(task.getStatus(), updateMapped.getStatus());
    }

    @Test
    void testNullSafety() {
        assertNull(getUserMapper.toDto(null));
        assertNull(getUserMapper.toEntity(null));
        assertNull(createUserMapper.toDto(null));
        assertNull(createUserMapper.toEntity(null));
        assertNull(projectMapper.toDto(null));
        assertNull(projectMapper.toEntity(null));
        assertNull(getTaskMapper.toDto(null));
        assertNull(getTaskMapper.toEntity(null));
        assertNull(createTaskMapper.toDto(null));
        assertNull(createTaskMapper.toEntity(null));
        assertNull(updateStatusTaskMapper.toDto(null));
        assertNull(updateStatusTaskMapper.toEntity(null));
    }

    @Test
    void testNestedMapping() {
        User creator = new User(4L, "dave", "password", Collections.emptyList(), Collections.emptyList());
        User assignee = new User(5L, "eve", "password", Collections.emptyList(), Collections.emptyList());
        Task task = new Task(200L, "Tâche 2", TaskStatus.DONE, null, assignee);
        Project project = new Project(30L, "Projet Z", creator, List.of(task));
        task.setProject(project);
        creator.setProjects(List.of(project));
        assignee.setTasks(List.of(task));

        ProjectDTO projectDTO = projectMapper.toDto(project);
        assertEquals("Projet Z", projectDTO.getName());
        assertEquals(4L, projectDTO.getCreatorId());
    }
}