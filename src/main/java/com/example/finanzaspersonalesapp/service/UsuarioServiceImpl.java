package com.example.finanzaspersonalesapp.service;

import com.example.finanzaspersonalesapp.model.Usuario;
import com.example.finanzaspersonalesapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override public List<Usuario> findAll() { return usuarioRepository.findAll(); }
    @Override public Optional<Usuario> findById(Long id) { return usuarioRepository.findById(id); }
    @Override public Usuario save(Usuario u) { return usuarioRepository.save(u); }

    @Override
    public Usuario update(Long id, Usuario u) {
        return usuarioRepository.findById(id).map(existing -> {
            existing.setNombre(u.getNombre());
            existing.setEmail(u.getEmail());
            existing.setPassword(u.getPassword());
            return usuarioRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
    }

    @Override public void delete(Long id) { usuarioRepository.deleteById(id); }
    @Override public Optional<Usuario> login(String email, String password) {
        return usuarioRepository.findByEmailAndPassword(email, password);
    }
}
