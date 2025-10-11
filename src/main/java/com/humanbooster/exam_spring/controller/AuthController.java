package com.humanbooster.exam_spring.controller;

import com.humanbooster.exam_spring.dto.auth.LoginDTO;
import com.humanbooster.exam_spring.dto.auth.RegisterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    // private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(LoginDTO loginDTO) {
        // TODO Implémenter la connexion
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Connexion non implémentée");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(RegisterDTO registerDTO) {
        // TODO Implémenter l'inscription
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Inscription non implémentée");
    }

}
