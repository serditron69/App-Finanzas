package com.example.finanzaspersonalesapp.controller;

import com.example.finanzaspersonalesapp.model.Transaccion;
import com.example.finanzaspersonalesapp.service.TransaccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
@Tag(name = "Transacciones", description = "Ingresos y gastos + reportes para gráficos")
public class TransaccionController {
    private final TransaccionService transaccionService;

    @Operation(summary = "Listar todas las transacciones")
    @GetMapping
    public List<Transaccion> findAll() { return transaccionService.findAll(); }

    @Operation(summary = "Obtener transacción por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> findById(@PathVariable Long id) {
        return transaccionService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registrar transacción")
    @PostMapping
    public Transaccion save(@RequestBody Transaccion t) { return transaccionService.save(t); }

    @Operation(summary = "Actualizar transacción")
    @PutMapping("/{id}")
    public ResponseEntity<Transaccion> update(@PathVariable Long id, @RequestBody Transaccion t) {
        try { return ResponseEntity.ok(transaccionService.update(id, t)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar transacción")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transaccionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ─── CONSULTAS NATIVAS ────────────────────────────────────────────────────

    @Operation(summary = "[NATIVA] Transacciones de un usuario en un mes y año")
    @GetMapping("/usuario/{idUsuario}/mes/{mes}/anio/{anio}")
    public List<Transaccion> findByMes(@PathVariable Long idUsuario,
                                       @PathVariable int mes,
                                       @PathVariable int anio) {
        return transaccionService.findByUsuarioMesAnio(idUsuario, mes, anio);
    }

    @Operation(summary = "[NATIVA] Resumen mensual: total ingresos y gastos (gráfico mensual)")
    @GetMapping("/usuario/{idUsuario}/resumen/mes/{mes}/anio/{anio}")
    public List<Object[]> findResumenMensual(@PathVariable Long idUsuario,
                                              @PathVariable int mes,
                                              @PathVariable int anio) {
        return transaccionService.findResumenMensual(idUsuario, mes, anio);
    }

    @Operation(summary = "[NATIVA] Balance semanal día a día (gráfico semanal)")
    @GetMapping("/usuario/{idUsuario}/balance/semanal")
    public List<Object[]> findBalanceSemanal(@PathVariable Long idUsuario) {
        return transaccionService.findBalanceSemanal(idUsuario);
    }

    @Operation(summary = "[NATIVA] Balance mes a mes de un año completo (gráfico anual)")
    @GetMapping("/usuario/{idUsuario}/balance/anual/{anio}")
    public List<Object[]> findBalanceAnual(@PathVariable Long idUsuario, @PathVariable int anio) {
        return transaccionService.findBalanceAnualPorMes(idUsuario, anio);
    }

    @Operation(summary = "[NATIVA] Gastos por categoría en un mes (gráfico de torta)")
    @GetMapping("/usuario/{idUsuario}/gastos/categoria/mes/{mes}/anio/{anio}")
    public List<Object[]> findGastosPorCategoria(@PathVariable Long idUsuario,
                                                  @PathVariable int mes,
                                                  @PathVariable int anio) {
        return transaccionService.findGastosPorCategoria(idUsuario, mes, anio);
    }

    @Operation(summary = "[NATIVA] Top 5 categorías con más gastos históricos")
    @GetMapping("/usuario/{idUsuario}/top5-categorias")
    public List<Object[]> findTop5(@PathVariable Long idUsuario) {
        return transaccionService.findTop5CategoriasGastos(idUsuario);
    }

    @Operation(summary = "[NATIVA] Balance neto (ingresos - gastos) por mes del año")
    @GetMapping("/usuario/{idUsuario}/balance-neto/anio/{anio}")
    public List<Object[]> findBalanceNeto(@PathVariable Long idUsuario, @PathVariable int anio) {
        return transaccionService.findBalanceNetoPorMes(idUsuario, anio);
    }
}
