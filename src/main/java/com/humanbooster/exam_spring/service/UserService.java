package com.humanbooster.exam_spring.service;

import com.humanbooster.exam_spring.model.User;
import com.humanbooster.exam_spring.repository.UserRepository;
import com.humanbooster.exam_spring.service.generic.GenericJPAService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserService extends GenericJPAService<User, Long> implements UserDetailsService {
    private final UserRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        super(userRepository);
        this.utilisateurRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Tentative de chargement de l'utilisateur: {}", username);
        return utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Utilisateur non trouvé: {}", username);
                    return new UsernameNotFoundException("Utilisateur non trouvé: " + username);
                });
    }

    @Override
    public User create(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return super.create(entity);
    }

    @Override
    public Optional<User> update(User newEntity, Long id) {
        newEntity.setPassword(passwordEncoder.encode(newEntity.getPassword()));
        return super.update(newEntity, id);
    }
}
