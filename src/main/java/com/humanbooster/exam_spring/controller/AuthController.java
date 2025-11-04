package com.humanbooster.exam_spring.controller;

import com.humanbooster.exam_spring.dto.auth.LoginDTO;
import com.humanbooster.exam_spring.dto.auth.RegisterDTO;
import com.humanbooster.exam_spring.dto.auth.RegisterMapper;
import com.humanbooster.exam_spring.dto.user.GetUserDTO;
import com.humanbooster.exam_spring.dto.user.GetUserMapper;
import com.humanbooster.exam_spring.model.User;
import com.humanbooster.exam_spring.service.JwtService;
import com.humanbooster.exam_spring.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final RegisterMapper registerMapper;
    private final GetUserMapper getUserMapper;
    private final com.humanbooster.exam_spring.utils.SecurityUtil securityUtil;
    private final com.humanbooster.exam_spring.service.CookieService cookieService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        log.info("Tentative de connexion pour l'utilisateur: {}", loginDTO.getUsername());
        // Authentifier l'utilisateur
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );
        // Récupérer l'utilisateur pour obtenir son id
        User user = (User) userService.loadUserByUsername(loginDTO.getUsername());
        // Générer l'access token avec l'id utilisateur dans le payload
        String accessToken = jwtService.generateToken(user.getUsername(), user.getId());

        // Déposer le token dans un cookie HTTP-only nommé "access_token"
        response.addCookie(cookieService.createAccessTokenCookie(accessToken));

        log.info("Utilisateur connecté avec succès: {}", loginDTO.getUsername());
        return ResponseEntity.ok(new AuthResponse("User logged in successfully", getUserMapper.toDto(user)));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterDTO registerDTO, HttpServletResponse response) {
        log.info("Tentative d'inscription pour l'utilisateur: {}", registerDTO.getUsername());
        // Créer le nouvel utilisateur
        User savedUser = userService.create(registerMapper.toEntity(registerDTO));
        // Générer l'access token avec l'id utilisateur dans le payload
        String accessToken = jwtService.generateToken(savedUser.getUsername(), savedUser.getId());

        // Déposer le token dans un cookie HTTP-only nommé "access_token"
        response.addCookie(cookieService.createAccessTokenCookie(accessToken));

        log.info("Utilisateur créé avec succès: {}", savedUser.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse("User registered successfully", getUserMapper.toDto(savedUser)));
    }

    @GetMapping("/verify")
    public ResponseEntity<AuthResponse> verify() {
        // Si cette méthode est appelée, cela signifie que l'utilisateur est authentifié (grâce au filtre JWT)
        log.info("Vérification de l'authentification réussie");

        // Utiliser l'utilitaire pour récupérer les infos utilisateur depuis le contexte de sécurité
        GetUserDTO userDto = securityUtil.getCurrentUserFromAuthentification();

        if (userDto != null) {
            return ResponseEntity.ok(new AuthResponse("User is authenticated (token present)", userDto));
        }

        return ResponseEntity.ok(new AuthResponse("User is authenticated", null));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Supprime le cookie d'accès côté client
        response.addCookie(cookieService.createClearAccessTokenCookie());
        return ResponseEntity.noContent().build();
    }

    @Data
    @AllArgsConstructor
    public static class AuthResponse {
        private String message;
        private GetUserDTO user;
    }
}
