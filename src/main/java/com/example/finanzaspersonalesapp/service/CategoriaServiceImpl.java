package com.example.finanzaspersonalesapp.service;

import com.example.finanzaspersonalesapp.model.Categoria;
import com.example.finanzaspersonalesapp.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired private CategoriaRepository categoriaRepository;

    @Override public List<Categoria> findAll() { return categoriaRepository.findAll(); }
    @Override public Optional<Categoria> findById(Long id) { return categoriaRepository.findById(id); }
    @Override public List<Categoria> findByTipo(String tipo) { return categoriaRepository.findByTipo(tipo); }
    @Override public Categoria save(Categoria c) { return categoriaRepository.save(c); }

    @Override
    public Categoria update(Long id, Categoria c) {
        return categoriaRepository.findById(id).map(existing -> {
            existing.setNombre(c.getNombre());
            existing.setTipo(c.getTipo());
            existing.setIcono(c.getIcono());
            return categoriaRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + id));
    }

    @Override public void delete(Long id) { categoriaRepository.deleteById(id); }
    @Override public List<Categoria> findCategoriasUsadasPorUsuario(Long idUsuario) {
        return categoriaRepository.findCategoriasUsadasPorUsuario(idUsuario);
    }
}
