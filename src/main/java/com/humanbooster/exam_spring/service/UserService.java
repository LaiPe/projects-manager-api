package com.humanbooster.exam_spring.service;

import com.humanbooster.exam_spring.model.User;
import com.humanbooster.exam_spring.repository.UserRepository;
import com.humanbooster.exam_spring.service.generic.GenericJPAService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService extends GenericJPAService<User, Long>{

    public UserService(UserRepository userRepository) {
        super(userRepository);
    }
}
