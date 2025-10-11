package com.humanbooster.exam_spring.controller;

import com.humanbooster.exam_spring.dto.auth.LoginDTO;
import com.humanbooster.exam_spring.dto.auth.RegisterDTO;
import com.humanbooster.exam_spring.dto.auth.RegisterMapper;
import com.humanbooster.exam_spring.model.User;
import com.humanbooster.exam_spring.service.JwtService;
import com.humanbooster.exam_spring.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final RegisterMapper registerMapper;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) {
        log.info("Tentative de connexion pour l'utilisateur: {}", loginDTO.getUsername());
        try {
            // Authentifier l'utilisateur
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );

            // Générer l'access token
            String accessToken = jwtService.generateToken(loginDTO.getUsername());

            log.info("Utilisateur connecté avec succès: {}", loginDTO.getUsername());
            return ResponseEntity.ok(accessToken);

        } catch (BadCredentialsException e) {
            log.warn("Tentative de connexion échouée pour l'utilisateur: {}", loginDTO.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect");
        } catch (Exception e) {
            log.error("Erreur lors de l'authentification: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO registerDTO) {
        log.info("Tentative d'inscription pour l'utilisateur: {}", registerDTO.getUsername());
        try {
            // Créer le nouvel utilisateur
            User savedUser = userService.create(registerMapper.toEntity(registerDTO));

            // Générer l'access token
            String accessToken = jwtService.generateToken(savedUser.getUsername());

            log.info("Utilisateur créé avec succès: {}", savedUser.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(accessToken);
        } catch (IllegalArgumentException e) {
            log.warn("Nom d'utilisateur déjà utilisé: {}", registerDTO.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            log.error("Erreur lors de la création de l'utilisateur: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
