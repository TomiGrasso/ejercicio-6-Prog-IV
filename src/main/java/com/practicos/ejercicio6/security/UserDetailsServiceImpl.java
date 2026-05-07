package com.practicos.ejercicio6.security;

import com.practicos.ejercicio6.model.Usuario;
import com.practicos.ejercicio6.store.UsuarioStore;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioStore usuarioStore;

    public UserDetailsServiceImpl(UsuarioStore usuarioStore) {
        this.usuarioStore = usuarioStore;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Usuario usuario = usuarioStore.buscarUsuarioPorUsername(username);

        if (usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        return usuario;
    }

}
