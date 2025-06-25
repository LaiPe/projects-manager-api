package com.humanbooster.exam_spring.service;

import com.humanbooster.exam_spring.model.Project;
import com.humanbooster.exam_spring.model.User;
import com.humanbooster.exam_spring.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project save(Project project){
        return projectRepository.save(project);
    }

    @Transactional(readOnly = true)
    public Optional<Project> findById(Long id){
        return projectRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Project> findProjectsByCreatorId(Long creatorId){
        User user = new User();
        user.setId(creatorId);
        return projectRepository.findProjectsByCreator(user);
    }

}
