package com.example.finanzaspersonalesapp.controller;

import com.example.finanzaspersonalesapp.model.Alerta;
import com.example.finanzaspersonalesapp.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
@Tag(name = "Alertas", description = "Alertas de gasto por presupuesto")
public class AlertaController {
    private final AlertaService alertaService;

    @Operation(summary = "Listar todas las alertas")
    @GetMapping
    public List<Alerta> findAll() { return alertaService.findAll(); }

    @Operation(summary = "Obtener alerta por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Alerta> findById(@PathVariable Long id) {
        return alertaService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Alertas de un usuario")
    @GetMapping("/usuario/{idUsuario}")
    public List<Alerta> findByUsuario(@PathVariable Long idUsuario) {
        return alertaService.findByUsuario(idUsuario);
    }

    @Operation(summary = "[NATIVA] Alertas no leídas de un usuario")
    @GetMapping("/usuario/{idUsuario}/no-leidas")
    public List<Alerta> findNoLeidas(@PathVariable Long idUsuario) {
        return alertaService.findNoLeidas(idUsuario);
    }

    @Operation(summary = "Crear alerta")
    @PostMapping
    public Alerta save(@RequestBody Alerta alerta) { return alertaService.save(alerta); }

    @Operation(summary = "Marcar alerta como leída")
    @PatchMapping("/{id}/leer")
    public ResponseEntity<Alerta> marcarLeida(@PathVariable Long id) {
        try { return ResponseEntity.ok(alertaService.marcarLeida(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar alerta")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
