package com.practicos.ejercicio6.service;

import com.practicos.ejercicio6.dto.RegisterRequestDTO;

import com.practicos.ejercicio6.model.Usuario;
import com.practicos.ejercicio6.store.UsuarioStore;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private final UsuarioStore usuarioStore;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioStore usuarioStore, PasswordEncoder passwordEncoder){
        this.usuarioStore = usuarioStore;
        this.passwordEncoder = passwordEncoder;
    }

    public void registrar(RegisterRequestDTO dto) {
        Usuario usuario = toUsuarioFromRegisterDTO(dto);
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_PACIENTE");
        usuario.setRoles(roles);

        usuarioStore.guardarUsuario(usuario);
    }

    private Usuario toUsuarioFromRegisterDTO(RegisterRequestDTO registerDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(registerDTO.getUsername());
        usuario.setEmail(registerDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        return usuario;
    }
}
