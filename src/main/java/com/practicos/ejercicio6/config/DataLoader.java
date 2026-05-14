package com.practicos.ejercicio6.config;

import com.practicos.ejercicio6.model.Usuario;
import com.practicos.ejercicio6.store.UsuarioStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initUsuarios(UsuarioStore usuarioStore, PasswordEncoder encoder){
        return args -> {
            List<String> rolesAdmin = new ArrayList<>();
            rolesAdmin.add("ROLE_ADMIN");
            //Inicializo un usuario de prueba con "ROLE_ADMIN"
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123"));
            admin.setEmail("admin@turnos.com");
            admin.setRoles(rolesAdmin);

            usuarioStore.guardarUsuario(admin);

            List<String> rolesMedico = new ArrayList<>();
            rolesMedico.add("ROLE_MEDICO");
            //Inicializo un usuario de prueba con "ROLE_MEDICO"
            Usuario medico = new Usuario();
            medico.setUsername("dr_garcia");
            medico.setPassword(encoder.encode("medico123"));
            medico.setEmail("medico@turnos.com");
            medico.setRoles(rolesMedico);
            usuarioStore.guardarUsuario(medico);
        };
    }
}
