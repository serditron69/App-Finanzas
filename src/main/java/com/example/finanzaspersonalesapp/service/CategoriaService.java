package com.example.finanzaspersonalesapp.service;

import com.example.finanzaspersonalesapp.model.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> findAll();
    Optional<Categoria> findById(Long id);
    List<Categoria> findByTipo(String tipo);
    Categoria save(Categoria categoria);
    Categoria update(Long id, Categoria categoria);
    void delete(Long id);
    List<Categoria> findCategoriasUsadasPorUsuario(Long idUsuario);
}
