package com.practicos.ejercicio6.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    private List<Map<String, Object>> turnos = new ArrayList<>();
    public TurnoController() {
        Map<String, Object> turno1 = new HashMap<>();
        turno1.put("id", 1L);
        turno1.put("paciente", "juan");
        turno1.put("medico", "dr_garcia");
        turno1.put("fecha", "2026-05-01");
        turnos.add(turno1);
        Map<String, Object> turno2 = new HashMap<>();
        turno2.put("id", 2L);
        turno2.put("paciente", "maria");
        turno2.put("medico", "dr_garcia");
        turno2.put("fecha", "2026-05-02");
        turnos.add(turno2);
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listarTurnos() {
        return ResponseEntity.ok(turnos);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearTurno(@RequestBody Map<String, Object> datosTurno, Authentication auth) {
        Map<String, Object> turnoNuevo = new HashMap<>();
        turnoNuevo.put("id", (long) (turnos.size() + 1));
        turnoNuevo.put("paciente", auth.getName());
        turnoNuevo.put("medico", datosTurno.get("medico"));
        turnoNuevo.put("fecha", datosTurno.get("fecha"));
        turnos.add(turnoNuevo);
        return ResponseEntity.status(201).body(turnoNuevo);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MEDICO') and @turnoSecurit yService.esMedicoDelTurno(authentication, #id))")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id, Authentication auth) {
        Map<String, Object> turnoAEliminar = null;
        for (Map<String, Object> t : turnos) {
            Long turnoId = (Long) t.get("id");
            if (turnoId.equals(id)) {
                turnoAEliminar = t;
                break;
            }
        }
        if (turnoAEliminar == null) {
            return ResponseEntity.status(404).body("Turno no encontrado");
        }
        turnos.remove(turnoAEliminar);
        return ResponseEntity.noContent().build();
    }
}
