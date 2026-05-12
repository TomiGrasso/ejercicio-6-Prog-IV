package com.practicos.ejercicio6.store;

import com.practicos.ejercicio6.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioStore {

    private List<Usuario> usuarios = new ArrayList<>();

    private Long contadorId = 1L;

    /*
    public UsuarioStore {
        List<String> rolesAdmin = new ArrayList<>();
        rolesAdmin.add("ROLE_ADMIN");
        //Inicializo un usuario de prueba con "ROLE_ADMIN"
        Usuario admin = new Usuario(
                contadorId++,
                "admin",
                encoder.encode("admin123"),
                "admin@turnos.com",
                rolesAdmin
        );
        usuarios.add(admin);

        List<String> rolesMedico = new ArrayList<>();
        rolesMedico.add("ROLE_MEDICO");
        Inicializo un usuario de prueba con "ROLE_MEDICO"
        Usuario medico = new Usuario(
                contadorId++,
                "dr_garcia",
                encoder.encode("medico123"),
                "medico@turnos.com",
                rolesMedico);
        usuarios.add(medico);
    }
     */

    public void guardarUsuario(Usuario usuario){
        usuario.setId(contadorId++);
        usuarios.add(usuario);
    }

    public Usuario buscarUsuarioPorUsername(String username){
        for (Usuario u : usuarios){
            if (u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    public boolean existeUsername(String username){
        for (Usuario u : usuarios){
            if (u.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean existeEmail(String email){
        for (Usuario u : usuarios){
            if (u.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public List<Usuario> obtenerTodos(){
        return usuarios;
    }
}
