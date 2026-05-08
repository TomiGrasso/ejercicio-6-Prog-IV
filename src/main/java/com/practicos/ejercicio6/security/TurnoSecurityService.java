package com.practicos.ejercicio6.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("turnoSecurityService")
public class TurnoSecurityService {

    public boolean esMedicoDelTurno(Authentication authentication, Long turnoId, List<Map<String, Object>> turnos) {
        String medicoUsername = authentication.getName();
        for (Map<String, Object> turno : turnos) {
            Long id = (Long) turno.get("id");
            if (id.equals(turnoId)) {
                String medicoDelTurno = (String) turno.get("medico");
                return medicoDelTurno.equals(medicoUsername);
            }
        }
        return false;
    }
}
