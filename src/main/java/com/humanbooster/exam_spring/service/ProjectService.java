package com.humanbooster.exam_spring.service;

import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.model.User;
import com.humanbooster.exam_spring.repository.ProjectRepository;
import com.humanbooster.exam_spring.service.generic.GenericJPAService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectService extends GenericJPAService<Project, Long> {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        super(projectRepository);
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public List<Project> findProjectsByCreatorId(Long creatorId){
        User user = new User();
        user.setId(creatorId);
        return projectRepository.findProjectsByCreator(user);
    }

}
