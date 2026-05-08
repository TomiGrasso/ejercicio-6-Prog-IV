package com.practicos.ejercicio6.controller;

import com.practicos.ejercicio6.dto.AuthResponseDTO;
import com.practicos.ejercicio6.dto.LoginRequestDTO;
import com.practicos.ejercicio6.dto.RegisterRequestDTO;
import com.practicos.ejercicio6.model.Usuario;
import com.practicos.ejercicio6.security.JwtUtils;
import com.practicos.ejercicio6.service.AuthService;
import com.practicos.ejercicio6.store.UsuarioStore;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UsuarioStore usuarioStore;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthService authService, UsuarioStore usuarioStore, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authService = authService;
        this.usuarioStore = usuarioStore;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@Valid @RequestBody RegisterRequestDTO dto){
        if (usuarioStore.existeUsername(dto.getUsername())){
            return ResponseEntity.status(409).body("El username ya está en uso");
        }
        if (usuarioStore.existeEmail(dto.getEmail())){
            return ResponseEntity.status(409).body("El mail ya está en uso");
        }

        authService.crear(dto);
        return ResponseEntity.status(201).body("Usuario registrado correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> logear(@Valid @RequestBody LoginRequestDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        Usuario usuario = (Usuario) auth.getPrincipal();

        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : usuario.getAuthorities()) {
            roles.add(authority.getAuthority());
        }

        String token = jwtUtils.generarToken(usuario.getUsername(), roles);

        AuthResponseDTO response = new AuthResponseDTO(token, 3600, usuario.getUsername());
        return ResponseEntity.ok(response);
    }
}
