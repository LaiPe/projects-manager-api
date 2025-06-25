package com.humanbooster.exam_spring.repository;

import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findProjectsByCreator(User creator);
}
