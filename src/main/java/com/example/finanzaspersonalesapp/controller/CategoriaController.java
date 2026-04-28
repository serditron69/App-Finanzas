package com.example.finanzaspersonalesapp.controller;

import com.example.finanzaspersonalesapp.model.Categoria;
import com.example.finanzaspersonalesapp.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorías", description = "Categorías de ingresos y gastos")
public class CategoriaController {

    @Autowired private CategoriaService categoriaService;

    @Operation(summary = "Listar todas las categorías")
    @GetMapping
    public List<Categoria> findAll() { return categoriaService.findAll(); }

    @Operation(summary = "Obtener categoría por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Long id) {
        return categoriaService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar categorías por tipo (INGRESO o GASTO)")
    @GetMapping("/tipo/{tipo}")
    public List<Categoria> findByTipo(@PathVariable String tipo) { return categoriaService.findByTipo(tipo); }

    @Operation(summary = "[NATIVA] Categorías que usa un usuario específico")
    @GetMapping("/usuario/{idUsuario}/usadas")
    public List<Categoria> findUsadas(@PathVariable Long idUsuario) {
        return categoriaService.findCategoriasUsadasPorUsuario(idUsuario);
    }

    @Operation(summary = "Crear categoría")
    @PostMapping
    public Categoria save(@RequestBody Categoria categoria) { return categoriaService.save(categoria); }

    @Operation(summary = "Actualizar categoría")
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria categoria) {
        try { return ResponseEntity.ok(categoriaService.update(id, categoria)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @Operation(summary = "Eliminar categoría")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
