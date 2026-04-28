package com.example.finanzaspersonalesapp.controller;

import com.example.finanzaspersonalesapp.model.Presupuesto;
import com.example.finanzaspersonalesapp.service.PresupuestoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/presupuestos")
@Tag(name = "Presupuestos", description = "Presupuestos mensuales por categoría")
public class PresupuestoController {

    @Autowired private PresupuestoService presupuestoService;

    @Operation(summary = "Listar todos los presupuestos")
    @GetMapping
    public List<Presupuesto> findAll() { return presupuestoService.findAll(); }

    @Operation(summary = "Obtener presupuesto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Presupuesto> findById(@PathVariable Long id) {
        return presupuestoService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Presupuestos de un usuario")
    @GetMapping("/usuario/{idUsuario}")
    public List<Presupuesto> findByUsuario(@PathVariable Long idUsuario) {
        return presupuestoService.findByUsuario(idUsuario);
    }

    @Operation(summary = "Crear presupuesto")
    @PostMapping
    public Presupuesto save(@RequestBody Presupuesto p) { return presupuestoService.save(p); }

    @Operation(summary = "Actualizar presupuesto")
    @PutMapping("/{id}")
    public ResponseEntity<Presupuesto> update(@PathVariable Long id, @RequestBody Presupuesto p) {
        try { return ResponseEntity.ok(presupuestoService.update(id, p)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar presupuesto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        presupuestoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "[NATIVA] Presupuestos excedidos de un usuario en un mes")
    @GetMapping("/usuario/{idUsuario}/excedidos/mes/{mes}/anio/{anio}")
    public List<Object[]> findExcedidos(@PathVariable Long idUsuario,
                                         @PathVariable int mes,
                                         @PathVariable int anio) {
        return presupuestoService.findPresupuestosExcedidos(idUsuario, mes, anio);
    }
}
