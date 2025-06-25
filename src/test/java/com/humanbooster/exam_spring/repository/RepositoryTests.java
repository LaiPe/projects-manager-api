package com.humanbooster.exam_spring.repository;

import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.model.Task;
import com.humanbooster.exam_spring.model.TaskStatus;
import com.humanbooster.exam_spring.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testFindProjectsByCreator() {
        User creator = new User();
        creator.setUsername("testuser");
        entityManager.persist(creator);

        Project project = new Project();
        project.setName("Test Project");
        project.setCreator(creator);
        entityManager.persist(project);
        entityManager.flush();

        List<Project> foundProjects = projectRepository.findProjectsByCreator(creator);

        assertThat(foundProjects).hasSize(1);
        assertThat(foundProjects.getFirst().getCreator()).isEqualTo(creator);
    }

    @Test
    public void testFindTasksByAssignee() {
        User creator = new User();
        creator.setUsername("creator");
        entityManager.persist(creator);
        
        User assignee = new User();
        assignee.setUsername("assignee");
        entityManager.persist(assignee);

        Project project = new Project();
        project.setName("Test Project");
        project.setCreator(creator);
        entityManager.persist(project);

        Task task = new Task();
        task.setTitle("Test Task");
        task.setStatus(TaskStatus.TODO);
        task.setProject(project);
        task.setAssignee(assignee);
        entityManager.persist(task);
        entityManager.flush();

        List<Task> foundTasks = taskRepository.findTasksByAssignee(assignee);

        assertThat(foundTasks).hasSize(1);
        assertThat(foundTasks.get(0).getAssignee()).isEqualTo(assignee);
    }

    @Test
    public void testFindTaskByProject() {
        User creator = new User();
        creator.setUsername("creator");
        entityManager.persist(creator);

        User assignee = new User();
        assignee.setUsername("assignee");
        entityManager.persist(assignee);

        Project project = new Project();
        project.setName("Test Project");
        project.setCreator(creator);
        entityManager.persist(project);

        Task task = new Task();
        task.setTitle("Test Task");
        task.setStatus(TaskStatus.TODO);
        task.setProject(project);
        task.setAssignee(assignee);
        entityManager.persist(task);
        entityManager.flush();

        List<Task> foundTasks = taskRepository.findTaskByProject(project);

        assertThat(foundTasks).hasSize(1);
        assertThat(foundTasks.get(0).getProject()).isEqualTo(project);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("newuser");
        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("newuser");
    }
} 